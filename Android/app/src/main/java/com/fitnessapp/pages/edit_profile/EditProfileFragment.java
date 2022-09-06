package com.fitnessapp.pages.edit_profile;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentEditProfileBinding;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.profile.ProfileViewModel;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;

public class EditProfileFragment extends Fragment {
    ProfileViewModel profileViewModel;
    ProfileModel data;
    FragmentEditProfileBinding editProfileViewBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ProfileViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        editProfileViewBinding = FragmentEditProfileBinding.inflate(inflater,container,false);
        editProfileViewBinding.btnCancel.setOnClickListener((v)-> Navigation.findNavController(v).popBackStack());
        editProfileViewBinding.btnSave.setOnClickListener((v)->Navigation.findNavController(v).popBackStack());
        return editProfileViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindobservers(view);
    }

    void bindobservers(View v)
    {
        profileViewModel.liveGetProfile.observe(getViewLifecycleOwner(),
                (it)->handleGetProfileObserver(it));
    }
    void handleGetProfileObserver(NetworkResult<ProfileResponseModel> it)
    {
        if(it.getClass().equals((SuccessResult.class)))
        {
            data = profileViewModel.liveGetProfile.getValue().getData().data;
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
        editProfileViewBinding.editTextName.setText(data.name);
        editProfileViewBinding.editTextAge.setText(data.age);
    }
}