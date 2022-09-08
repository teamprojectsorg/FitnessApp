package com.fitnessapp.network;

import com.fitnessapp.pages.capture.CaptureAPI;
import com.fitnessapp.pages.goals.PrefrenceAPI;
import com.fitnessapp.pages.login_signup.LoginSignUpAPI;
import com.fitnessapp.pages.profile.ProfileAPI;
import com.fitnessapp.pages.profile.ProfileRepository;
import com.fitnessapp.pages.progress.ProgressAPI;

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
    public CaptureAPI providesCaptureAPI()
    {
        return this.providesRetrofit().create(CaptureAPI.class);
    }
    public ProfileAPI providesProfileAPI()
    {
        return this.providesRetrofit().create(ProfileAPI.class);
    }
    public PrefrenceAPI providesPreferenceAPI()
    {
        return this.providesRetrofit().create(PrefrenceAPI.class);
    }
    public ProgressAPI providesProgressAPI()
    {
        return this.providesRetrofit().create(ProgressAPI.class);
    }
}
