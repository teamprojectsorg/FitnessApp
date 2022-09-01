package com.fitnessapp.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.fitnessapp.R;
import com.fitnessapp.databinding.LoginBinder;

public class LoginActivity extends AppCompatActivity {
    private LoginBinder binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }
}