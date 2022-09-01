package com.fitnessapp.activities.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitnessapp.repositories.PreferencesRepository;

import java.util.prefs.Preferences;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<LoginModel> loginMutableLive = new MutableLiveData<>();

    @Inject
    private PreferencesRepository preferencesRepository;

    public LoginViewModel()
    {
        loginMutableLive.setValue(new LoginModel());
    }

    public void onButtonClick()
    {

    }
}
