package com.fitnessapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fitnessapp.R;

public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
        }

        @Override
        protected void onStart() {
                super.onStart();
        }
}