package com.fitnessapp.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentHomeBinding;
import com.fitnessapp.databinding.FragmentLoginBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding viewBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHomeBinding.inflate(inflater,container,false);
        return viewBinding.getRoot();
    }
}