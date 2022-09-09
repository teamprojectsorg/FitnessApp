package com.fitnessapp.pages.goals;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.pages.goals.models.PreferenceResponseModel;
import com.fitnessapp.pages.goals.models.PrefernceModel;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface PrefrenceAPI {
    @GET("user/preference")
    Call<PreferenceResponseModel> getPreference(@Header("Authorization") String token);
    @PUT("user/preference")
    Call<ApiResponseModel> putPreference(@Header("Authorization") String token,
                                      @Body PrefernceModel prefernce);
}
