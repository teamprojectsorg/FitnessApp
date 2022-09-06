package com.fitnessapp.pages.capture;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.pages.capture.models.CaptureModel;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CaptureAPI {
    @GET("consumption/weekly")
    Call<CaptureResponseModel> getWeeklyCapture(@Header("Authorization") String token,
                                                @Query("weeks") int weeks,
                                                @Query("date") String date);
    @GET("consumption/daily")
    Call<CaptureResponseModel> getDailyCapture(@Header("Authorization") String token,
                                               @Query("days") int days,
                                               @Query("date") String date);
    @POST("consumption/add")
    Call<ApiResponseModel> postCapture(@Header("Authorization") String token,
                                       @Body CaptureModel capture);
}
