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
import com.fitnessapp.databinding.FragmentLoginBinding;
import com.fitnessapp.pages.login_signup.LoginSignUpViewModel;
import com.fitnessapp.pages.login_signup.models.LoginSignUpModel;
import com.fitnessapp.utils.network_utils.results.ErrorResult;
import com.fitnessapp.utils.network_utils.results.SuccessResult;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    FragmentLoginBinding viewBinding;
    LoginSignUpViewModel loginViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginViewModel = ViewModelProviders.of(this)
                        .get(LoginSignUpViewModel.class);

        viewBinding = FragmentLoginBinding.inflate(inflater,container,false);

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
        viewBinding.btnLogin.setOnClickListener((v)->logIn(v));
        viewBinding.newUser.setOnClickListener((v)->
        {
            Navigation.findNavController(v).popBackStack();
        });
    }

    private void bindObserver(View view)
    {
        loginViewModel.liveResponse.observe(getViewLifecycleOwner(),
                (it) ->
                {
                    viewBinding.progressCircular.setEnabled(false);

                    if(it.getClass().equals((SuccessResult.class)))
                    {
                        Navigation.findNavController(view)
                                .navigate(R.id.action_loginFragment_to_homeFragment);
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
    public void logIn(View v) {
        if(!validateUsername() | !validatePassword()) {
            return;
        }
        LoginSignUpModel logInModel = new LoginSignUpModel(getUsername(),
                getPassword());
        //TODO To be handeled
        //loginViewModel.logIn(logInModel);
        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    private String getUsername()
    {
        return viewBinding.username.getText().toString();
    }
    private String getPassword()
    {
        return viewBinding.password.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}