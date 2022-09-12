package com.fitnessapp.pages.progress;

import com.fitnessapp.pages.capture.models.CaptureResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ProgressAPI {
    @GET("consumption/lifetime")
    Call<CaptureResponseModel> getLifeTimeDailyConsumption(@Header("Authorization") String token);
}
