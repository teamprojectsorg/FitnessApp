package com.fitnessapp.pages.login_signup.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentSignupBinding;
import com.fitnessapp.pages.login_signup.LoginSignUpViewModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.utils.network_utils.results.ErrorResult;
import com.fitnessapp.utils.network_utils.results.SuccessResult;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment {
    FragmentSignupBinding viewBinding;
    LoginSignUpViewModel signUpViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signUpViewModel = ViewModelProviders.of(this)
                .get(LoginSignUpViewModel.class);

        viewBinding = FragmentSignupBinding.inflate(inflater,container,false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListeners();
        bindObserver(view);
    }
    private void setListeners()
    {
        viewBinding.btnRegister.setOnClickListener((v)->register(v));
    }

    private void bindObserver(View view)
    {
        signUpViewModel.liveResponse.observe(getViewLifecycleOwner(),
                (it) ->
                {
                    viewBinding.progressCircular.setEnabled(false);

                    if(it.getClass().equals((SuccessResult.class)))
                    {
                        Navigation.findNavController(view)
                                .navigate(R.id.action_SignUpFragment_to_loginFragment);
                    }
                    else if(it.getClass().equals((ErrorResult.class)))
                    {
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Error")
                                .setMessage(it.getMessage())
                                .show();
                    }
                    else
                    {
                        viewBinding.progressCircular.setEnabled(true);
                    }
                });
    }

    private boolean validateUsername()  {
        String userInput = getUsername().trim();
        if(userInput.isEmpty()) {
            viewBinding.username.setError("Field cannot be empty");
            return false;
        }else {
            viewBinding.username.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = getPassword().trim();
        if(passwordInput.isEmpty()) {
            viewBinding.password.setError("Field cannot be empty");
            return false;
        }else {
            viewBinding.password.setError(null);
            return true;
        }
    }
    private boolean validateRepeatPass() {
        String repeatPassInput = viewBinding.repeatpass.getEditText().getText().toString().trim();
        if(repeatPassInput.isEmpty()) {
            viewBinding.repeatpass.setError("Field cannot be empty");
            return false;
        }
        else if(viewBinding.password.equals(viewBinding.repeatpass))
        {
            viewBinding.repeatpass.setError("Password donot match");
            return false;
        }
        else {
            viewBinding.password.setError(null);
            return true;
        }
    }
    public void register(View v) {
        if(!validateUsername() | !validatePassword() | !validateRepeatPass()) {
            return;
        }
        LoginSignUpModel signUpModel = new LoginSignUpModel(getUsername(),
                                                            getPassword());
        signUpViewModel.signUp(signUpModel);
    }

    private String getUsername()
    {
        return viewBinding.username.getEditText().getText().toString();
    }
    private String getPassword()
    {
        return viewBinding.password.getEditText().getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}