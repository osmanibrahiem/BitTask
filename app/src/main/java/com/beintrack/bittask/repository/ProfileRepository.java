package com.beintrack.bittask.repository;

import android.content.Context;

import com.beintrack.bittask.model.ApiResponse;
import com.beintrack.bittask.model.Image;
import com.beintrack.bittask.model.Profile;
import com.beintrack.bittask.repository.api.ApiServer;
import com.beintrack.bittask.repository.api.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfileRepository {

    private static ProfileRepository instance;

    private ApiServer apiServer;

    public ProfileRepository(Context context) {
        apiServer = RetrofitClient.getInstance(context).getService();
    }

    public static ProfileRepository getInstance(Context context) {
        if (instance == null)
            instance = new ProfileRepository(context);
        return instance;
    }

    private Observable<ApiResponse<Profile>> getProfileData() {
        return apiServer
                .getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<ApiResponse<List<Image>>> getProfileImages() {
        return apiServer
                .getProfileImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ApiResponse<Profile>> getProfile() {
        return Observable.zip(getProfileData(), getProfileImages(), (profileResponse, imagesResponse) -> {
            Profile profile = profileResponse.getData();
            profile.setImageList(imagesResponse.getData());
            profileResponse.setData(profile);
            profileResponse.setStatus(profileResponse.getStatus() && imagesResponse.getStatus());
            return profileResponse;
        });
    }
}
