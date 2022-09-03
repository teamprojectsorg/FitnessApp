package com.fitnessapp.pages.login_signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpResponseModel;
import com.fitnessapp.utils.network_utils.NetworkResult;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginSignUpViewModel extends ViewModel {
    private LoginSignUpRepository loginSignUpRepository;

    public LiveData<NetworkResult<LoginSignUpResponseModel>> liveResponse = loginSignUpRepository.getLiveResponse();

    @Inject
    public LoginSignUpViewModel(LoginSignUpRepository loginSignUpRepository)
    {
        this.loginSignUpRepository = loginSignUpRepository;
    }

    public void signUp(LoginSignUpModel signUpModel)
    {
        loginSignUpRepository.signUp(signUpModel);
    }

    public void logIn(LoginSignUpModel logInModel)
    {
        loginSignUpRepository.logIn(logInModel);
    }
}
