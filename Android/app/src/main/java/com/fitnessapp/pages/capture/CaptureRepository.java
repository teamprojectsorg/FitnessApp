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

import org.json.JSONObject;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptureRepository {
    public CaptureAPI captureAPI;

    public MutableLiveData<NetworkResult<CaptureResponseModel>> getCaptureResponse = new MutableLiveData<>();
    public MutableLiveData<NetworkResult<ApiResponseModel>> postCaptureResponse = new MutableLiveData<>();
    private String token = "Bearer " + new SharedPreferencesRepository().getToken();
    public CaptureRepository()
    {
        NetworkModule network = new NetworkModule();
        captureAPI = network.providesCaptureAPI();
    }

    public void getDailyCaptures()
    {
        getCaptureResponse.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleGetResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String date = LocalDate.now().toString();

            Call call = captureAPI.getDailyCapture(token,
                    9,date);
            call.enqueue(callBack);
        }
    }
    public void getWeeklyCaptures()
    {
        getCaptureResponse.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleGetResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String date = LocalDate.now().toString();
            Call call =  captureAPI.getWeeklyCapture(token,
            9,date);
            call.enqueue(callBack);
        }

    }
    private void handleGetResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
           getCaptureResponse.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                getCaptureResponse.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                getCaptureResponse.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            getCaptureResponse.postValue(new ErrorResult(response.message()));
        }
    }

    public void addCapture(CaptureModel capture)
    {
        getCaptureResponse.postValue(new LoadingResult<>());
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
            postCaptureResponse.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                postCaptureResponse.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                postCaptureResponse.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            postCaptureResponse.postValue(new ErrorResult(response.message()));
        }
    }
}
