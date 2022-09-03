package com.fitnessapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fitnessapp.MainApplication;

public class SharedPreferencesRepository {
    private String DEFAULT_FILE_NAME = "default_shared_preference";
    private String LOGGED_IN = "logged_in";

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
}
