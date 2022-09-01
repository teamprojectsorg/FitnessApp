package com.fitnessapp.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class PreferencesRepository {
    private String DEFAULT_FILE_NAME = "DefaultSharedPreference";

    private SharedPreferences preference;

    @Inject
    public PreferencesRepository(@ApplicationContext Context context)
    {
        preference = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public boolean isLogedIn()
    {
        return false;
    }
}
