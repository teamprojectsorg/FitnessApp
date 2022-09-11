package com.fitnessapp.pages.progress;

import androidx.lifecycle.MutableLiveData;

import com.fitnessapp.network.NetworkModule;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.LoadingResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.CaptureAPI;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;
import com.fitnessapp.repositories.SharedPreferencesRepository;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressRepository {
    public ProgressAPI progressAPI;

    public MutableLiveData<NetworkResult<CaptureResponseModel>> liveGetLifetimeDailyConsumption = new MutableLiveData<>();
    private String token = "Bearer " + new SharedPreferencesRepository().getToken();
public ProgressRepository()
{
    NetworkModule network = new NetworkModule();
    progressAPI = network.providesProgressAPI();
}
    public void getLifetimeDailyConsumption()
    {
        liveGetLifetimeDailyConsumption.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleGetDiseaseRiskResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call = progressAPI.getLifeTimeDailyConsumption(token);
        call.enqueue(callBack);
    }
    private void handleGetDiseaseRiskResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            liveGetLifetimeDailyConsumption.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                liveGetLifetimeDailyConsumption.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                liveGetLifetimeDailyConsumption.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            liveGetLifetimeDailyConsumption.postValue(new ErrorResult(response.message()));
        }
    }
}
