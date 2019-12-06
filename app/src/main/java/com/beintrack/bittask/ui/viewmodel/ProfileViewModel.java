package com.beintrack.bittask.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.beintrack.bittask.model.ErrorResponse;
import com.beintrack.bittask.model.Profile;
import com.beintrack.bittask.repository.ProfileRepository;
import com.beintrack.bittask.repository.api.CallbackWrapper;
import com.beintrack.bittask.ui.view.base.BaseViewModel;
import com.beintrack.bittask.ui.view.profile.ProfileNavigation;

public class ProfileViewModel extends BaseViewModel<ProfileNavigation> {

    private static ProfileRepository repository;

    public MutableLiveData<Profile> profileData;
    public MutableLiveData<ErrorResponse> errorData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = ProfileRepository.getInstance(application);
        profileData = new MutableLiveData<>();
        errorData = new MutableLiveData<>();
    }

    public LiveData<Profile> getProfile() {
        setIsLoading(true);
        repository.getProfile().subscribe(new CallbackWrapper<Profile>(getApplication()) {
            @Override
            protected void onSuccess(Profile profile) {
                setIsLoading(false);
                if (profile != null) {
                    profileData.setValue(profile);
                    errorData.setValue(null);
                }
            }

            @Override
            protected void onError(ErrorResponse error) {
                setIsLoading(false);
                profileData.setValue(null);
                errorData.setValue(error);
            }
        });
        return profileData;
    }
}
