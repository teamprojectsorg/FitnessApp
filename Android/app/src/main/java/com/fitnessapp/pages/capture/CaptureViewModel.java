package com.fitnessapp.pages.capture;

import androidx.lifecycle.LiveData;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.pages.capture.models.CaptureModel;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;

public class CaptureViewModel {
    private CaptureRepository captureRepository;

    public LiveData<NetworkResult<CaptureResponseModel>> getCaptureResponse;
    public LiveData<NetworkResult<ApiResponseModel>> postResponse;

    public CaptureViewModel()
    {
        this.captureRepository = new CaptureRepository();
        this.getCaptureResponse = captureRepository.getCaptureResponse;
        this.postResponse = captureRepository.postCaptureResponse;
    }
    public void getDailyCapture()
    {
        captureRepository.getDailyCaptures();
    }
    public void getWeeklyCapture(){captureRepository.getWeeklyCaptures();}
    public void addCapture(CaptureModel capture)
    {
        captureRepository.addCapture(capture);
    }
}
