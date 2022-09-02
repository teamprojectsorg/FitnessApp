package com.fitnessapp.activities.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitnessapp.models.LoginSignUpModel;
import com.fitnessapp.repositories.PreferencesRepository;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<LoginSignUpModel> loginMutableLive = new MutableLiveData<>();

    @Inject
    private PreferencesRepository preferencesRepository;

    public LoginViewModel()
    {
        loginMutableLive.setValue(new LoginSignUpModel());
    }

    public void onButtonClick()
    {
    }
}
