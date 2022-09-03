package com.fitnessapp.pages.login_signup.models;

import com.fitnessapp.models.ApiResponseModel;

public class LoginSignUpResponseModel extends ApiResponseModel {
    public LoginSignUpModel data;

    public LoginSignUpModel getData() {
        return data;
    }

    public void setData(LoginSignUpModel data) {
        this.data = data;
    }
}
