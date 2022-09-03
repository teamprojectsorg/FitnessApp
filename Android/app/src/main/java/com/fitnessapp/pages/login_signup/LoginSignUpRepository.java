package com.fitnessapp.pages.login_signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitnessapp.network.NetworkModule;
import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpResponseModel;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.LoadingResult;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.SuccessResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignUpRepository {
    public LoginSignUpAPI loginSignUpAPI;

    private MutableLiveData<NetworkResult<LoginSignUpResponseModel>> _mutableResponse = new MutableLiveData<>();

    private Callback callBack;

    public LoginSignUpRepository()
    {
        NetworkModule network = new NetworkModule();
        loginSignUpAPI = network.providesLoginSignUpAPI(network.providesRetrofit());

        initCallBack();
    }
    private void initCallBack()
    {
        callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
    }

    public void signUp(LoginSignUpModel model)
    {
        _mutableResponse.postValue(new LoadingResult<>());
        Call call =  loginSignUpAPI.signUp(model);
        call.enqueue(callBack);
    }
    public void logIn(LoginSignUpModel model)
    {
        _mutableResponse.postValue(new LoadingResult<>());
        Call call =  loginSignUpAPI.logIn(model);
        call.enqueue(callBack);
    }

    private void handleResponse(Response response)
    {
        if(response.isSuccessful() && response.body() != null)
        {
            _mutableResponse.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            _mutableResponse.postValue(new ErrorResult("Something went wrong"));
        }
        else
        {
            _mutableResponse.postValue(new ErrorResult("Something went wrong"));
        }
    }
    public LiveData<NetworkResult<LoginSignUpResponseModel>> getLiveResponse()
    {
        return  _mutableResponse;
    }
}
