package com.fitnessapp.pages.capture;

import androidx.lifecycle.LiveData;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.pages.capture.models.CaptureModel;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;

public class CaptureViewModel {
    private ConsumptionRepository captureRepository;

    public LiveData<NetworkResult<CaptureResponseModel>> liveGetDailyConsumption;
    public LiveData<NetworkResult<CaptureResponseModel>> liveGetWeeklyConsumption;
    public LiveData<NetworkResult<ApiResponseModel>> postResponse;

    public CaptureViewModel()
    {
        this.captureRepository = new ConsumptionRepository();
        this.liveGetWeeklyConsumption = captureRepository.liveWeeklyConsumption;
        this.liveGetDailyConsumption = captureRepository.liveDailyConsumtion;
        this.postResponse = captureRepository.liveAddConsumtion;
    }
    public void getDailyCapture()
    {
        captureRepository.getDailyConsumption();
    }
    public void getWeeklyCapture(){captureRepository.getWeeklyCaptures();}
    public void addCapture(CaptureModel capture)
    {
        captureRepository.addCapture(capture);
    }
}
