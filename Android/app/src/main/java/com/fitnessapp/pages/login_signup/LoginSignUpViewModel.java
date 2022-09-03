package com.fitnessapp.pages.login_signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpResponseModel;
import com.fitnessapp.repositories.SharedPreferencesRepository;
import com.fitnessapp.network.NetworkResult;

public class LoginSignUpViewModel extends ViewModel {
    public LoginSignUpRepository loginSignUpRepository;
    public SharedPreferencesRepository sharedPreferencesRepository;

    public LiveData<NetworkResult<LoginSignUpResponseModel>> liveResponse;

    public LoginSignUpViewModel()
    {
        this.loginSignUpRepository = new LoginSignUpRepository();
        this.liveResponse = loginSignUpRepository.getLiveResponse();
        this.sharedPreferencesRepository = new SharedPreferencesRepository();
    }

    public void signUp(LoginSignUpModel signUpModel)
    {
        loginSignUpRepository.signUp(signUpModel);
    }

    public void logIn(LoginSignUpModel logInModel)
    {
        loginSignUpRepository.logIn(logInModel);
    }

    public void setupLoggedIn()
    {
        sharedPreferencesRepository.setLoggedIn(true);
    }
}
