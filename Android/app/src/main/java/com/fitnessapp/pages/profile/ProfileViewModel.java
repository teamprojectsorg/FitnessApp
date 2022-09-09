package com.fitnessapp.pages.profile;

import androidx.lifecycle.LiveData;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.pages.capture.CaptureRepository;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;

public class ProfileViewModel {
    private ProfileRepository profileRepository;

    public LiveData<NetworkResult<ProfileResponseModel>> liveGetProfile;
    public LiveData<NetworkResult<ApiResponseModel>> livePutProfile;

    public ProfileViewModel()
    {
        this.profileRepository = new ProfileRepository();
        this.liveGetProfile = profileRepository.liveGetProfile;
        this.livePutProfile = profileRepository.livePutProfile;
    }
    public void getProfile(){profileRepository.getProfile();}
    public void putProfile(ProfileModel profile){profileRepository.putProfile(profile);}
}
