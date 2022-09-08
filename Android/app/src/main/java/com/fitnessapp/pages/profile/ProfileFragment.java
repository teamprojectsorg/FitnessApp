package com.fitnessapp.pages.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentProfileBinding;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.CaptureFragment;
import com.fitnessapp.pages.edit_profile.EditProfileFragment;
import com.fitnessapp.pages.profile.models.ProfileModel;
import com.fitnessapp.pages.profile.models.ProfileResponseModel;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    ProfileViewModel profileViewModel;
    ProfileModel data;

    private CardView editProfileCard, capturizerCard;
    FragmentProfileBinding viewBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ProfileViewModel();
        profileViewModel.getProfile();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindObservers();
    }
    void bindObservers()
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
        String username = data.username;
        viewBinding.lblUsername.setText(username);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentProfileBinding.inflate(inflater,container,false);
        initCard();
        return viewBinding.getRoot();
    }
    private void initCard()
    {
        editProfileCard =viewBinding.editProfileCard;
        capturizerCard = viewBinding.capturizerCard;
        //add click listener to the cards
        editProfileCard.setOnClickListener((v)->editProfileClick());
        capturizerCard.setOnClickListener((v)->catureClick());
    }
    public void editProfileClick()
    {
        Navigation.findNavController(viewBinding.getRoot()).navigate(R.id.action_profileFragment_to_editProfileFragment);
    }
    public void catureClick()
    {
        Navigation.findNavController(viewBinding.getRoot()).navigate(R.id.action_profileFragment_to_captureFragment);
    }
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.edit_profile_card: Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_editProfileFragment);
            case R.id.capturizer_card: Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_captureFragment);
            default:break;
        }
    }
}