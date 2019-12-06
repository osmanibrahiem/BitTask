package com.beintrack.bittask.repository.api;

import android.content.Context;

import com.beintrack.bittask.R;
import com.beintrack.bittask.helper.NetworkUtils;
import com.beintrack.bittask.model.ApiResponse;
import com.beintrack.bittask.model.ErrorResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public abstract class CallbackWrapper<T> extends DisposableObserver<ApiResponse<T>> {

    private Context context;

    public CallbackWrapper(Context context) {
        this.context = context;
    }

    protected abstract void onSuccess(T t);

    protected abstract void onError(ErrorResponse error);

    @Override
    public void onNext(ApiResponse<T> response) {
        if (response != null && response.getStatus() && response.getData() != null) {
            onSuccess(response.getData());
        } else {
            onError(new ErrorResponse(R.drawable.error_no_data, context.getString(R.string.error_header_no_data), context.getString(R.string.error_message_no_data)));
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!NetworkUtils.isNetworkConnected(context) || e instanceof IOException) {
            onError(new ErrorResponse(R.drawable.error_no_internet, context.getString(R.string.error_header_no_internet), context.getString(R.string.error_message_no_internet)));
        } else if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            Converter<ResponseBody, Error> errorConverter =
                    RetrofitClient.getInstance(context).getRetrofit().responseBodyConverter(Error.class, new Annotation[0]);
            try {
                Error error = errorConverter.convert(responseBody);
                onError(new ErrorResponse(R.drawable.error_404, context.getString(R.string.error_header_404), error.getMessage()));
            } catch (Exception e1) {
                onError(new ErrorResponse(R.drawable.error_404, context.getString(R.string.error_header_404), e1.getMessage()));
            }
        } else {
            onError(new ErrorResponse(R.drawable.error_404, context.getString(R.string.error_header_404), context.getString(R.string.error_message_404)));
        }
    }

    @Override
    public void onComplete() {

    }
}
