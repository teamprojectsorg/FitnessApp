package com.fitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.fitnessapp.R;
import com.fitnessapp.fragments.home.HomeFragment;
import com.fitnessapp.fragments.profile.ProfileFragment;
import com.fitnessapp.fragments.progress.ProgressFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        new HomeFragment()).commit();

        bottomNav.setOnItemSelectedListener(item->
        {
            Fragment selectedFragment = null;
            switch (item.getItemId())
            {
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_favorites:
                    selectedFragment = new ProgressFragment();
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            selectedFragment).commit();

            return true;
        });
    }
}