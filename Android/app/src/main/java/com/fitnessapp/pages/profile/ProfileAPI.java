package com.fitnessapp.pages.profile;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ProfileAPI {
    //TODO
    @GET("")
    Call<ProfileResponseModel> getProfile(@Header("Authorization") String token);
    @PUT("")
    Call<ApiResponseModel> putProfile(@Header("Authorization") String token,
                                      ProfileModel profile);
}
