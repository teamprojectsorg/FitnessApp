package com.fitnessapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fitnessapp.MainApplication;

public class SharedPreferencesRepository {
    private String DEFAULT_FILE_NAME = "default_shared_preference";
    private String LOGGED_IN = "logged_in";
    private String TOKEN = "token";

    private SharedPreferences sharedPreference;
    public SharedPreferencesRepository()
    {
        Context context = MainApplication.applicationContext;
        sharedPreference = context.getSharedPreferences(DEFAULT_FILE_NAME,context.MODE_PRIVATE);
    }
    public boolean getLoggedIn()
    {
        return sharedPreference.getBoolean(LOGGED_IN,false);
    }
    public void setLoggedIn(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putBoolean(LOGGED_IN,status);
        editor.apply();
    }

    public String getToken()
    {
        return sharedPreference.getString(TOKEN,"");
    }
    public void setToken(String token)
    {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(TOKEN,token);
        editor.apply();
    }
}
