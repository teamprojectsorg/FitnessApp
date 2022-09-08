package com.fitnessapp.pages.edit_profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding viewBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentEditProfileBinding.inflate(inflater, container, false);
        viewBinding.btnCancel.setOnClickListener((v)-> Navigation.findNavController(v).popBackStack());
        viewBinding.btnSave.setOnClickListener((v)->Navigation.findNavController(v).popBackStack());
        return viewBinding.getRoot();
    }
}