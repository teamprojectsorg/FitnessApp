package com.fitnessapp;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    public static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }
}
