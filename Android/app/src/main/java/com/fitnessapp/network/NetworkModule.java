package com.fitnessapp.network;

import com.fitnessapp.pages.login_signup.LoginSignUpAPI;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {
    private String BASE_URL = "https://fitnessappnode.azurewebsites.net";
    public Retrofit providesRetrofit()
    {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }
    public LoginSignUpAPI providesLoginSignUpAPI(Retrofit retrofit)
    {
        return retrofit.create(LoginSignUpAPI.class);
    }
}
