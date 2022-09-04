package com.fitnessapp.pages.login_signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fitnessapp.pages.login_signup.models.LoginResponseModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.SignUpResponseModel;
import com.fitnessapp.repositories.SharedPreferencesRepository;
import com.fitnessapp.network.NetworkResult;

public class LoginSignUpViewModel extends ViewModel {
    public LoginSignUpRepository loginSignUpRepository;
    public SharedPreferencesRepository sharedPreferencesRepository;

    public LiveData<NetworkResult<SignUpResponseModel>> signupResponse;
    public LiveData<NetworkResult<LoginResponseModel>> loginResponse;

    public boolean isLoggedIn;

    public LoginSignUpViewModel()
    {
        this.loginSignUpRepository = new LoginSignUpRepository();
        this.signupResponse = loginSignUpRepository.getSignupResponse();
        this.loginResponse = loginSignUpRepository.getLoginResponse();
        this.sharedPreferencesRepository = new SharedPreferencesRepository();
        isLoggedIn = sharedPreferencesRepository.getLoggedIn();
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
        String token = loginResponse.getValue().getData().data.accessToken;
        sharedPreferencesRepository.setToken(token);
        sharedPreferencesRepository.setLoggedIn(true);
    }
}
