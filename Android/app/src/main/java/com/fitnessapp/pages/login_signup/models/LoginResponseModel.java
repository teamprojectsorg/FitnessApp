package com.fitnessapp.pages.login_signup.models;

import com.fitnessapp.models.ApiResponseModel;

public class LoginResponseModel extends ApiResponseModel {
    public AuthModel data;
    public class AuthModel
    {
        public String accessToken;
        public String refreshToken;
    }
}
