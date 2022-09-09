
package com.fitnessapp.pages.goals;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentGoalBinding;
import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.goals.models.PreferenceResponseModel;
import com.fitnessapp.pages.goals.models.PrefernceModel;
import com.fitnessapp.pages.profile.ProfileViewModel;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;

public class GoalFragment extends Fragment {
    PreferenceViewModel preferenceViewModel;
    PrefernceModel data;

    FragmentGoalBinding viewBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceViewModel = new PreferenceViewModel();
        preferenceViewModel.getPreferences();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindObservers();
    }
    void bindObservers()
    {
        preferenceViewModel.liveGetPreference.observe(getViewLifecycleOwner(),
                (it)->handleGetPreferencesObserver(it));
        preferenceViewModel.livePutPreference.observe(getViewLifecycleOwner(),
                (it)-> handlePutPreferencesObserver(it));
    }
    void handlePutPreferencesObserver(NetworkResult<ApiResponseModel> it)
    {
        if(it.getClass().equals((SuccessResult.class)))
        {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Success")
                    .setMessage("Data Saved")
                    .show();
        }
        else if(it.getClass().equals((ErrorResult.class)))
        {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }

    void handleGetPreferencesObserver(NetworkResult<PreferenceResponseModel> it)
    {
        if(it.getClass().equals((SuccessResult.class)))
        {
            data = preferenceViewModel.liveGetPreference.getValue().getData().data;
            setUiFields();
        }
        else if(it.getClass().equals((ErrorResult.class)))
        {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }
    void setUiFields()
    {
        viewBinding.editTextCurrentIntake.setText(data.currentIntake);
        viewBinding.editTextPreviousIntake.setText(data.previousIntake);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentGoalBinding.inflate(inflater, container, false);
        viewBinding.btnUpdate.setOnClickListener((v)->onUpdateButtonClick(v));
        return viewBinding.getRoot();
    }

    public void onUpdateButtonClick(View v)
    {
        PrefernceModel prefernce = new PrefernceModel();
        prefernce.currentIntake = viewBinding.editTextCurrentIntake.getText().toString();
        prefernce.previousIntake = viewBinding.editTextPreviousIntake.getText().toString();
        preferenceViewModel.putPreferences(prefernce);
    }
}