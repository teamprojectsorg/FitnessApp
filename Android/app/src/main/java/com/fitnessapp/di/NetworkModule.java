package com.fitnessapp.di;

import com.fitnessapp.pages.login_signup.LoginSignUpAPI;
import com.fitnessapp.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public class NetworkModule {
    @Singleton
    @Provides
    public Retrofit providesRetrofit()
    {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    public LoginSignUpAPI providesLoginSignUpAPI(Retrofit retrofit)
    {
        return retrofit.create(LoginSignUpAPI.class);
    }
}
