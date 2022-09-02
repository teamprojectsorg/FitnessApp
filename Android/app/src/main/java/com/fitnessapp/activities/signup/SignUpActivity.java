package com.fitnessapp.activities.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.fitnessapp.R;
import com.fitnessapp.activities.login.LoginActivity;
import com.fitnessapp.activities.login.LoginViewModel;
import com.fitnessapp.databinding.LoginBinder;
import com.fitnessapp.databinding.SignUpBinder;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    private SignUpBinder binding;
    private SignUpViewModel viewModel;

    private TextInputLayout textInputPassword;
    private TextInputLayout textInputRepeatPassword;

    private TextInputLayout textInputUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        textInputUsername = findViewById(R.id.username);
        textInputPassword = findViewById(R.id.password);
        textInputRepeatPassword = findViewById(R.id.repeatpass);
    }
    private boolean validateUsername()  {
        String userInput = textInputUsername.getEditText().getText().toString().trim();
        if(userInput.isEmpty()) {
            textInputUsername.setError("Field cannot be empty");
            return false;
        }else {
            textInputUsername.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if(passwordInput.isEmpty()) {
            textInputPassword.setError("Field cannot be empty");
            return false;
        }else {
            textInputPassword.setError(null);
            return true;
        }
    }
    private boolean validateRepeatPass() {
        String repeatPassInput = textInputRepeatPassword.getEditText().getText().toString().trim();
        if(repeatPassInput.isEmpty()) {
            textInputRepeatPassword.setError("Field cannot be empty");
            return false;
        }else {
            textInputRepeatPassword.setError(null);
            return true;
        }
    }
    public void register(View v) {
        if(!validateUsername() | !validatePassword() | !validateRepeatPass()) {
            return;
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        viewModel.onRegisterButtonClick();

        String input = "Username: " + textInputUsername.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + textInputUsername.getEditText().getText().toString();
        input += "\n";
        input += "RepeatPassowrd: " + textInputUsername.getEditText().getText().toString();
        input += "\n";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}