package com.fitnessapp.pages.progress;

import androidx.lifecycle.LiveData;

import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.pages.goals.PreferenceRepository;
import com.fitnessapp.pages.goals.models.PreferenceResponseModel;

public class ProgressViewModel {
    private ProgressRepository progressRepository;

    public LiveData<NetworkResult<DiseaseResponseModel>> liveGetDiseaseRisk;
    public ProgressViewModel()
    {
        this.progressRepository = new ProgressRepository();
        this.liveGetDiseaseRisk = progressRepository.liveGetDiseaseRisk;
    }
    public void getDiseaseRisk(){progressRepository.getDiseaseRisk();}
}
