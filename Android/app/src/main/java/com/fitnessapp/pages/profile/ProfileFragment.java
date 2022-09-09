package com.fitnessapp.pages.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentProfileBinding;
import com.fitnessapp.pages.capture.CaptureFragment;
import com.fitnessapp.pages.edit_profile.EditProfileFragment;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private CardView editProfileCard, capturizerCard;
    FragmentProfileBinding viewBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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