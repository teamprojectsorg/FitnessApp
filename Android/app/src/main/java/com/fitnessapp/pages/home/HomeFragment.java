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

    GraphView graphView;
    FragmentHomeBinding viewBinding;
    CaptureViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CaptureViewModel();
        getData();
    }
    void getData()
    {
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
        viewBinding.goalCard.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_editProfileFragment));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHomeBinding.inflate(inflater,container,false);
        graphView=(GraphView) viewBinding.idGraphView;
        initAxisTiles();
        initCircleMenu();
        initObservers();
        viewBinding.captureCardView.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_captureFragment));
        viewBinding.goalHomepageCard.setOnClickListener((v)->Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_goalFragment));
        return viewBinding.getRoot();
    }
    private void initObservers()
    {
        viewModel.getCaptureResponse.observe(getViewLifecycleOwner(),
                (it)->{
                    if(it.getClass().equals((SuccessResult.class)))
                    {
                        initGraph();
                    }
                    else if(it.getClass().equals((ErrorResult.class)))
                    {
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Error")
                                .setMessage(it.getMessage())
                                .show();
                    }
                });
    }

    private void initCircleMenu()
    {
        //circle Menu
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

    private void swithFragments(int i)
    {
        NavController nc = Navigation.findNavController(viewBinding.getRoot());
        switch(i)
        {
            case 0:
                break;
            case 1:
                nc.navigate(R.id.action_homeFragment_to_profileFragment);
                break;
            case 2:
                nc.navigate(R.id.action_homeFragment_to_progressFragment);
                break;
            case 3:
                new SharedPreferencesRepository().setLoggedIn(false);
                new SharedPreferencesRepository().setToken("");
                nc.navigate(R.id.action_homeFragment_to_loginFragment);
                break;
            case 4:
                //nc.navigate(R.id.action_homeFragment_to_captureFragment);
                break;
        }
    }

    private void initAxisTiles()
    {
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Intake(in ML)");
        gridLabel.setHorizontalAxisTitleTextSize(40);
    }

    private void initGraph()
    {
        graphView=(GraphView) viewBinding.idGraphView;
      //  final DateFormat dateTimeFormatter = DateFormat.getDateTimeInstance();
        //graphView = new BarGraphSeries<DataPoint>(context,"chart");

        //graphView = viewBinding.graph;
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint()) ;
        graphView.removeAllSeries();
        graphView.addSeries(series);
        series.setDrawValuesOnTop( true ) ;
        series.setValuesOnTopColor( Color.RED ) ;
        series.setSpacing( 40 ) ;
        graphView.setTitle("Daily Intake");

        graphView.setTitleColor(Color.BLACK);

        graphView.setTitleTextSize(50);
    }

    private DataPoint[] getDataPoint() {
        CaptureModel[] data = viewModel.getCaptureResponse.getValue().getData().data;
        DataPoint[] dp = new DataPoint[data.length];
        for (int i=0 ; i< data.length;i++) {
            dp[i] = new DataPoint(i, Integer.parseInt(data[i].drinkIntake));
        }
        return dp;
    }
}