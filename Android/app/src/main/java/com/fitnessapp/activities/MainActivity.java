package com.fitnessapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fitnessapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
        private static final int ERROR_DIALOG_REQUEST = 9001;
        private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
        private static boolean mLocationPermissionGranted = false;
        //vars
        //private Boolean mLocationPermissionGranted = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                getLocationPermission();
        }

        @Override
        protected void onStart() {
                super.onStart();
        }

        private void getLocationPermission() {
                String[]  permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION};
                if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                                COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                mLocationPermissionGranted = true;
                        }else {
                                ActivityCompat.requestPermissions(this,permissions,
                                        LOCATION_PERMISSION_REQUEST_CODE);
                        }
                }else {
                        ActivityCompat.requestPermissions(this,permissions,
                                LOCATION_PERMISSION_REQUEST_CODE);
                }
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                mLocationPermissionGranted = false;
                switch(requestCode) {
                        case LOCATION_PERMISSION_REQUEST_CODE:{
                                if(grantResults.length > 0) {
                                        for(int i = 0; i < grantResults.length; i++) {
                                                if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                                        mLocationPermissionGranted = false;
                                                        return;
                                                }
                                        }
                                        mLocationPermissionGranted = true;
                                }
                        }
                }
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
}