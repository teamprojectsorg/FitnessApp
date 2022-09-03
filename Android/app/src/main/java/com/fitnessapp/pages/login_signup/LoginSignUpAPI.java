package com.fitnessapp.pages.login_signup;

import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpResponseModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignUpAPI {
    //TODO Url Mapping
    @POST("auth/signup")
    Call<LoginSignUpResponseModel> signUp(@Body LoginSignUpModel signUpModel);

    //TODO Url Mapping
    @POST("auth/login")
    Call<LoginSignUpResponseModel> logIn(@Body LoginSignUpModel logInModel);
}
