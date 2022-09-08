
package com.fitnessapp.pages.goals;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentGoalBinding;

public class GoalFragment extends Fragment {


    FragmentGoalBinding viewBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentGoalBinding.inflate(inflater, container, false);
        viewBinding.btnUpdate.setOnClickListener((v)->Navigation.findNavController(v).popBackStack());
        return viewBinding.getRoot();
    }


}