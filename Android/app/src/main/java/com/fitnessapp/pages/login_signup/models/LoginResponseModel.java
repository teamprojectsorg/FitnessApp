package com.fitnessapp.pages.login_signup.models;

import com.fitnessapp.models.ApiResponse;

public class LoginResponseModel extends ApiResponse {
    public AuthModel data;
    public class AuthModel
    {
        public String accessToken;
        public String refreshToken;
    }
}
