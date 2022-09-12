package com.fitnessapp.pages.progress;

import androidx.lifecycle.LiveData;

import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;
import com.fitnessapp.pages.goals.PreferenceRepository;
import com.fitnessapp.pages.goals.models.PreferenceResponseModel;

public class ProgressViewModel {
    private ProgressRepository progressRepository;

    public LiveData<NetworkResult<CaptureResponseModel>> liveGetLifetimeDailyConsumption;
    public ProgressViewModel()
    {
        this.progressRepository = new ProgressRepository();
        this.liveGetLifetimeDailyConsumption = progressRepository.liveGetLifetimeDailyConsumption;
    }
    public void getLifetimeDailyConsumption(){progressRepository.getLifetimeDailyConsumption();}
}
