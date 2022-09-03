package com.fitnessapp.pages.login_signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpResponseModel;
import com.fitnessapp.utils.network_utils.results.ErrorResult;
import com.fitnessapp.utils.network_utils.results.LoadingResult;
import com.fitnessapp.utils.network_utils.NetworkResult;
import com.fitnessapp.utils.network_utils.results.SuccessResult;

import javax.inject.Inject;

import retrofit2.Response;

public class LoginSignUpRepository {
    private LoginSignUpAPI loginSignUpAPI;

    private MutableLiveData<NetworkResult<LoginSignUpResponseModel>> _mutableResponse = new MutableLiveData<>();

    @Inject
    public LoginSignUpRepository(LoginSignUpAPI loginSignUpAPI)
    {
        this.loginSignUpAPI = loginSignUpAPI;
    }

    public void signUp(LoginSignUpModel signUpModel)
    {
        _mutableResponse.postValue(new LoadingResult<>());
        Response response =  loginSignUpAPI.signUp(signUpModel);
        handleResponse(response);
    }

    public void logIn(LoginSignUpModel logInModel)
    {
        _mutableResponse.postValue(new LoadingResult<>());
        Response response = loginSignUpAPI.logIn(logInModel);
        handleResponse(response);
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
