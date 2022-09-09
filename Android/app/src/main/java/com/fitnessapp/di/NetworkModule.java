package com.fitnessapp.di;

import com.fitnessapp.pages.login_signup.LoginSignUpAPI;
import com.fitnessapp.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {
    public Retrofit providesRetrofit()
    {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    public LoginSignUpAPI providesLoginSignUpAPI(Retrofit retrofit)
    {
        return retrofit.create(LoginSignUpAPI.class);
    }
}
