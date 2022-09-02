package com.fitnessapp.fragments.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.activities.NavigationActivity;
import com.fitnessapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    FragmentLoginBinding viewBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentLoginBinding.inflate(inflater,container,false);
        viewBinding.btnLogin.setOnClickListener((v)->
        {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeFragment);
        });
        return viewBinding.getRoot();
    }
}