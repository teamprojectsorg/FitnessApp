package com.fitnessapp.pages.login_signup;

import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpResponseModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignUpAPI {
    @POST("auth/signup")
    Call<LoginSignUpResponseModel> signUp(@Body LoginSignUpModel signUpModel);

    @POST("auth/login")
    Call<LoginSignUpResponseModel> logIn(@Body LoginSignUpModel logInModel);
}
