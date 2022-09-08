package com.fitnessapp.pages.login_signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitnessapp.network.NetworkModule;
import com.fitnessapp.pages.login_signup.models.LoginResponseModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.SignUpResponseModel;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.LoadingResult;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.SuccessResult;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignUpRepository {
    public LoginSignUpAPI loginSignUpAPI;

    private MutableLiveData<NetworkResult<SignUpResponseModel>> _signupResponse = new MutableLiveData<>();
    private MutableLiveData<NetworkResult<LoginResponseModel>> _loginResponse = new MutableLiveData<>();

    public LoginSignUpRepository()
    {
        NetworkModule network = new NetworkModule();
        loginSignUpAPI = network.providesLoginSignUpAPI(network.providesRetrofit());
    }

    public void signUp(LoginSignUpModel model)
    {
        _signupResponse.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleSignupResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call =  loginSignUpAPI.signUp(model);
        call.enqueue(callBack);
    }
    public void logIn(LoginSignUpModel model)
    {
        _loginResponse.postValue(new LoadingResult<>());
        Callback callBack  = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                handleLoginResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                new Exception(t);
            }
        };
        Call call =  loginSignUpAPI.logIn(model);
        call.enqueue(callBack);
    }

    private void handleSignupResponse(Response response) {
        if(response.isSuccessful() && response.body() != null)
        {
            _signupResponse.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                _signupResponse.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                _signupResponse.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            _signupResponse.postValue(new ErrorResult(response.message()));
        }
    }

    private void handleLoginResponse(Response response) {
        if(response.isSuccessful() && response.body() != null)
        {
            _loginResponse.postValue(new SuccessResult(response.body()));
        }
        else if(response.errorBody()!=null)
        {
            try {
                JSONObject json = new JSONObject(response.errorBody().string());
                _loginResponse.postValue(new ErrorResult(json.getString("message")));
            }
            catch (Exception ex)
            {
                _loginResponse.postValue(new ErrorResult(response.message()));
            }
        }
        else
        {
            _loginResponse.postValue(new ErrorResult(response.message()));
        }
    }
    public LiveData<NetworkResult<SignUpResponseModel>> getSignupResponse()
    {
        return  _signupResponse;
    }
    public LiveData<NetworkResult<LoginResponseModel>> getLoginResponse()
    {
        return  _loginResponse;
    }
}
