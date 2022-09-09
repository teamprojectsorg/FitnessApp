package com.fitnessapp.pages.goals;

import androidx.lifecycle.LiveData;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.pages.goals.models.PreferenceResponseModel;
import com.fitnessapp.pages.goals.models.PrefernceModel;
import com.fitnessapp.pages.profile.ProfileRepository;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;

public class PreferenceViewModel {
    private PreferenceRepository preferenceRepository;

    public LiveData<NetworkResult<PreferenceResponseModel>> liveGetPreference;
    public LiveData<NetworkResult<ApiResponseModel>> livePutPreference;

    public PreferenceViewModel()
    {
        this.preferenceRepository = new PreferenceRepository();
        this.liveGetPreference = preferenceRepository.liveGetPreferences;
        this.livePutPreference = preferenceRepository.livePutPreferences;
    }
    public void getPreferences(){preferenceRepository.getPreferences();}
    public void putPreferences(PrefernceModel prefernce){preferenceRepository.putPreferences(prefernce);}
}
