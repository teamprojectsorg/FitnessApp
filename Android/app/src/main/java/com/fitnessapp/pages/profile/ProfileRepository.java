package com.fitnessapp.pages.profile;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkModule;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.LoadingResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.CaptureAPI;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;
import com.fitnessapp.repositories.SharedPreferencesRepository;

import org.json.JSONObject;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    public ProfileAPI profileAPI;

    public MutableLiveData<NetworkResult<ProfileResponseModel>> liveGetProfile = new MutableLiveData<>();
    public MutableLiveData<NetworkResult<ApiResponseModel>> livePutProfile = new MutableLiveData<>();
    private String token = "Bearer " + new SharedPreferencesRepository().getToken();

    public ProfileRepository()
    {
        NetworkModule network = new NetworkModule();
        profileAPI = network.providesProfileAPI();
    }

    public void getProfile()
    {
        liveGetProfile.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleGetProfileResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call = profileAPI.getProfile(token);
        call.enqueue(callBack);
    }
    private void handleGetProfileResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            liveGetProfile.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                liveGetProfile.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                liveGetProfile.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            liveGetProfile.postValue(new ErrorResult(response.message()));
        }
    }
    public void putProfile(ProfileModel profile)
    {
        livePutProfile.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handlePutProfileResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call = profileAPI.putProfile(token,profile);
        call.enqueue(callBack);
    }
    private void handlePutProfileResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            livePutProfile.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                livePutProfile.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                livePutProfile.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            livePutProfile.postValue(new ErrorResult(response.message()));
        }
    }
}
