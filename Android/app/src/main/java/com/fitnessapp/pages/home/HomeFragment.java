package com.fitnessapp.pages.home;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentHomeBinding;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.models.CaptureModel;
import com.fitnessapp.pages.capture.CaptureViewModel;
import com.fitnessapp.repositories.SharedPreferencesRepository;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.ramotion.circlemenu.CircleMenuView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;

public class HomeFragment extends Fragment {

    FragmentHomeBinding viewBinding;
    CaptureViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CaptureViewModel();
        getData();
    }

    void getData() {
        viewModel.getDailyCapture();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false);

        initCircleMenu();

        viewBinding.helpCenterHomepageCard.setOnClickListener((v)->Navigation.findNavController(v).navigate((R.id.action_homeFragment_to_helpCenterFragment)));
        viewBinding.progressCardView.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_progressFragment));
        viewBinding.captureCardView.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_captureFragment));
        viewBinding.goalHomepageCard.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_goalFragment));
        viewBinding.locateRehabHomepageCard.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_locateRehabFragment));

        return viewBinding.getRoot();
    }

    private void initCircleMenu() {
        // circle Menu
        final CircleMenuView menu = viewBinding.circleMenu;
        menu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationStart");
            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuOpenAnimationEnd");
            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationStart");
            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {
                Log.d("D", "onMenuCloseAnimationEnd");
            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationStart|index: " + index);
                swithFragments(index);
            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd|index: " + index);
            }

            @Override
            public boolean onButtonLongClick(@NonNull CircleMenuView view, int buttonIndex) {
                Log.d("D", "onButtonLongClick|index: " + buttonIndex);
                return true;
            }

            @Override
            public void onButtonLongClickAnimationStart(@NonNull CircleMenuView view, int buttonIndex) {
                Log.d("D", "onButtonLongClickAnimationStart|index: " + buttonIndex);
            }

            @Override
            public void onButtonLongClickAnimationEnd(@NonNull CircleMenuView view, int buttonIndex) {
                Log.d("D", "onButtonLongClickAnimationEnd|index: " + buttonIndex);
            }
        });
    }

    private void swithFragments(int i) {
        NavController nc = Navigation.findNavController(viewBinding.getRoot());
        switch (i) {
            case 0:
                break;
            case 1:
                nc.navigate(R.id.action_homeFragment_to_profileFragment);
                break;
            case 2:
                // nc.navigate(R.id.action_homeFragment_to_progressFragment);
                break;
            case 3:
                new SharedPreferencesRepository().setLoggedIn(false);
                new SharedPreferencesRepository().setToken("");
                nc.navigate(R.id.action_homeFragment_to_loginFragment);
                break;
            case 4:
                // nc.navigate(R.id.action_homeFragment_to_captureFragment);
                break;
        }
    }
}