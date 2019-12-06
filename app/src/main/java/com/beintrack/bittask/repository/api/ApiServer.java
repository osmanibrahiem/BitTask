package com.beintrack.bittask.repository.api;

import com.beintrack.bittask.helper.Constants;
import com.beintrack.bittask.model.ApiResponse;
import com.beintrack.bittask.model.Image;
import com.beintrack.bittask.model.Profile;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiServer {

    @GET(Constants.PROFILE_END_POINT)
    Observable<ApiResponse<Profile>> getProfile();

    @GET(Constants.HOME_END_POINT)
    Observable<ApiResponse<List<Image>>> getProfileImages();
}
