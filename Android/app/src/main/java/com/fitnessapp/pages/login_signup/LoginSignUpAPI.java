package com.fitnessapp.pages.login_signup;

import com.fitnessapp.pages.login_signup.models.LoginResponseModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.SignUpResponseModel;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignUpAPI {

    @POST("auth/signup")
    Call<SignUpResponseModel> signUp(@Body LoginSignUpModel signUpModel);

    @POST("auth/login")
    Call<LoginResponseModel> logIn(@Body LoginSignUpModel logInModel);

}
