package com.fitnessapp.pages.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding viewBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHomeBinding.inflate(inflater,container,false);
        return viewBinding.getRoot();
    }
}