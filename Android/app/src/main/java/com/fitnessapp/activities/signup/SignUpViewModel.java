package com.fitnessapp.activities.signup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fitnessapp.models.LoginSignUpModel;
import com.fitnessapp.repositories.PreferencesRepository;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {
    public MutableLiveData<LoginSignUpModel> signUpMutableLive = new MutableLiveData<>();

    @Inject
    private PreferencesRepository preferencesRepository;

    public SignUpViewModel()
    {
        signUpMutableLive.setValue(new LoginSignUpModel());
    }

    public void onRegisterButtonClick()
    {

    }
}
