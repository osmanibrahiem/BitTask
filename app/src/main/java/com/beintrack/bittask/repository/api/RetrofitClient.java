package com.beintrack.bittask.repository.api;

import android.content.Context;

import com.beintrack.bittask.helper.Constants;
import com.beintrack.bittask.helper.NetworkUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;
    private final Retrofit retrofit;
    private ApiServer service;

    private RetrofitClient(Context context) {
        long cacheSize = (5 * 1024 * 1024);
        Cache myCache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.cache(myCache);

        httpClient.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();

            if (!NetworkUtils.isNetworkConnected(context)) {
                requestBuilder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7);
            } else {
                requestBuilder.addHeader("Cache-Control", "public, max-stale=" + 60);
            }

            return chain.proceed(requestBuilder.build());
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        this.retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    public synchronized static RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    public ApiServer getService() {
        if (service == null) {
            this.service = retrofit.create(ApiServer.class);
        }
        return service;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
