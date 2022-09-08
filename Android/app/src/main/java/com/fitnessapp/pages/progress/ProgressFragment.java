package com.fitnessapp.pages.progress;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.databinding.FragmentProgressBinding;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.models.CaptureModel;
import com.fitnessapp.pages.capture.CaptureViewModel;
import com.fitnessapp.pages.capture.models.CaptureResponseModel;
import com.fitnessapp.pages.goals.PreferenceViewModel;
import com.fitnessapp.pages.goals.models.PrefernceModel;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ProgressFragment extends Fragment {
    GraphView graphView;
    FragmentProgressBinding viewBinding;

    CaptureViewModel viewModel;
    CaptureModel[] data;

    ProgressViewModel progressViewModel;
    DiseaseResponseModel.DiseaseModel diseaseData;

    public ProgressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CaptureViewModel();
        viewModel.getWeeklyCapture();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentProgressBinding.inflate(inflater,container,false);

        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers(view);
    }
    private void initObservers(View v)
    {
        viewModel.getCaptureResponse.observe(getViewLifecycleOwner(),
                (it)->{
                    handelCaptureObserver(it);
                });
    }

    void handleDiseaseRiskObserver(NetworkResult<DiseaseResponseModel> it)
    {
        if(it.getClass().equals((SuccessResult.class)))
        {
            diseaseData = progressViewModel.liveGetDiseaseRisk.getValue().getData().data;
            viewBinding.lblDiseaseRisk.setText(diseaseData.diseaseRisk);
        }
        else if(it.getClass().equals((ErrorResult.class)))
        {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }

    void handelCaptureObserver(NetworkResult<CaptureResponseModel> it)
    {
        if(it.getClass().equals((SuccessResult.class)))
        {
            data = viewModel.getCaptureResponse.getValue().getData().data;
            viewBinding.lblUserIntake.setText(data[data.length-1].drinkIntake);
            viewBinding.lblDiseaseRisk.setText(data[data.length-1].diseaseRisk);
            initGraph();
        }
        else if(it.getClass().equals((ErrorResult.class)))
        {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }

    private void initGraph()
    {
        //graph initializing
        graphView=(GraphView) viewBinding.progressGraphView;
        //graphView = viewBinding.graph;
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint()) ;
        graphView.addSeries(series);
        series.setDrawValuesOnTop( true ) ;
        series.setValuesOnTopColor( Color.RED ) ;
        series.setSpacing( 40 ) ;
        series.setColor(Color.YELLOW);
        graphView.setTitle("Weekly Agregated Intake");

        graphView.setTitleColor(Color.BLACK);

        graphView.setTitleTextSize(50);
    }

    private DataPoint[] getDataPoint() {

        DataPoint[] dp = new DataPoint[data.length];
        for (int i=0 ; i< data.length;i++) {
            dp[i] = new DataPoint(i, Integer.parseInt(data[i].drinkIntake));
        }
        return dp;
    }
}