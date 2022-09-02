package com.fitnessapp.fragments.signup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentLoginBinding;
import com.fitnessapp.databinding.FragmentSignupBinding;

public class SignUpFragment extends Fragment {
    FragmentSignupBinding viewBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentSignupBinding.inflate(inflater,container,false);
        viewBinding.btnRegister.setOnClickListener((v)->register(v));
        return viewBinding.getRoot();
    }
    private boolean validateUsername()  {
        String userInput = viewBinding.username.getEditText().getText().toString().trim();
        if(userInput.isEmpty()) {
            viewBinding.username.setError("Field cannot be empty");
            return false;
        }else {
            viewBinding.username.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = viewBinding.password.getEditText().getText().toString().trim();
        if(passwordInput.isEmpty()) {
            viewBinding.password.setError("Field cannot be empty");
            return false;
        }else {
            viewBinding.password.setError(null);
            return true;
        }
    }
    private boolean validateRepeatPass() {
        String repeatPassInput = viewBinding.password.getEditText().getText().toString().trim();
        if(repeatPassInput.isEmpty()) {
            viewBinding.password.setError("Field cannot be empty");
            return false;
        }else {
            viewBinding.password.setError(null);
            return true;
        }
    }
    public void register(View v) {
        if(!validateUsername() | !validatePassword() | !validateRepeatPass()) {
            return;
        }
        Navigation.findNavController(v).navigate(R.id.action_SignUpFragment_to_loginFragment);
    }
}