package com.fitnessapp.pages.capture;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkModule;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.LoadingResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.models.CaptureModel;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;
import com.fitnessapp.repositories.SharedPreferencesRepository;
import com.fitnessapp.utils.Constants;

import org.json.JSONObject;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumptionRepository {
    public CaptureAPI captureAPI;

    public MutableLiveData<NetworkResult<CaptureResponseModel>> liveDailyConsumtion = new MutableLiveData<>();
    public MutableLiveData<NetworkResult<CaptureResponseModel>> liveWeeklyConsumption = new MutableLiveData<>();
    public MutableLiveData<NetworkResult<ApiResponseModel>> liveAddConsumtion = new MutableLiveData<>();
    private String token = "Bearer " + new SharedPreferencesRepository().getToken();
    int barCount = Constants.BAR_COUNT;
    public ConsumptionRepository()
    {
        NetworkModule network = new NetworkModule();
        captureAPI = network.providesCaptureAPI();
    }

    public void getDailyConsumption()
    {
        liveDailyConsumtion.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleGetResponse(response,liveDailyConsumtion);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String date = LocalDate.now().toString();

            Call call = captureAPI.getDailyCapture(token,
                    barCount,date);
            call.enqueue(callBack);
        }
    }

    public void getWeeklyCaptures()
    {
        liveWeeklyConsumption.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleGetResponse(response,liveWeeklyConsumption);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String date = LocalDate.now().toString();

            Call call = captureAPI.getWeeklyCapture(token,
                    barCount,date);
            call.enqueue(callBack);
        }
    }
    private void handleGetResponse(Response response,MutableLiveData<NetworkResult<CaptureResponseModel>> liveResponse)
    {
        if(response.isSuccessful() && response.body() != null)
        {
           liveResponse.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                liveResponse.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                liveResponse.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            liveResponse.postValue(new ErrorResult(response.message()));
        }
    }

    public void addCapture(CaptureModel capture)
    {
        liveAddConsumtion.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handlePostResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call =  captureAPI.postCapture(token,capture);
        call.enqueue(callBack);
    }
    private void handlePostResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            liveAddConsumtion.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                liveAddConsumtion.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                liveAddConsumtion.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            liveAddConsumtion.postValue(new ErrorResult(response.message()));
        }
    }
}
