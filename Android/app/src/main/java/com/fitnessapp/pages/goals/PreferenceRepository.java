package com.fitnessapp.pages.goals;

import androidx.lifecycle.MutableLiveData;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkModule;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.LoadingResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.goals.models.PreferenceResponseModel;
import com.fitnessapp.pages.goals.models.PrefernceModel;
import com.fitnessapp.pages.profile.ProfileAPI;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;
import com.fitnessapp.repositories.SharedPreferencesRepository;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferenceRepository {
    public PrefrenceAPI prefrenceAPI;

    public MutableLiveData<NetworkResult<PreferenceResponseModel>> liveGetPreferences = new MutableLiveData<>();
    public MutableLiveData<NetworkResult<ApiResponseModel>> livePutPreferences = new MutableLiveData<>();
    private String token = "Bearer " + new SharedPreferencesRepository().getToken();

    public PreferenceRepository()
    {
        NetworkModule network = new NetworkModule();
        prefrenceAPI = network.providesPreferenceAPI();
    }
    public void getPreferences()
    {
        liveGetPreferences.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleGetPreferencesResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call = prefrenceAPI.getPreference(token);
        call.enqueue(callBack);
    }
    private void handleGetPreferencesResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            liveGetPreferences.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                liveGetPreferences.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                liveGetPreferences.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            liveGetPreferences.postValue(new ErrorResult(response.message()));
        }
    }
    public void putPreferences(PrefernceModel preference)
    {
        livePutPreferences.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handlePutPreferenceResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call = prefrenceAPI.putPreference(token,preference);
        call.enqueue(callBack);
    }
    private void handlePutPreferenceResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            livePutPreferences.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                livePutPreferences.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                livePutPreferences.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            livePutPreferences.postValue(new ErrorResult(response.message()));
        }
    }
}
