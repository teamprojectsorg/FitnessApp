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

    public MutableLiveData<NetworkResult<DiseaseResponseModel>> liveGetDiseaseRisk = new MutableLiveData<>();
    private String token = "Bearer " + new SharedPreferencesRepository().getToken();
public ProgressRepository()
{
    NetworkModule network = new NetworkModule();
    progressAPI = network.providesProgressAPI();
}
    public void getDiseaseRisk()
    {
        liveGetDiseaseRisk.postValue(new LoadingResult<>());
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
        Call call = progressAPI.getDiseaseRisk(token);
        call.enqueue(callBack);
    }
    private void handleGetDiseaseRiskResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            liveGetDiseaseRisk.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                liveGetDiseaseRisk.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                liveGetDiseaseRisk.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            liveGetDiseaseRisk.postValue(new ErrorResult(response.message()));
        }
    }
}
