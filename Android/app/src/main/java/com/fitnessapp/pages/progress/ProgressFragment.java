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
import com.fitnessapp.pages.goals.models.PreferenceResponseModel;
import com.fitnessapp.pages.goals.models.PrefernceModel;
import com.fitnessapp.utils.Constants;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ProgressFragment extends Fragment {
    FragmentProgressBinding viewBinding;

    GraphView graphView;
    CaptureViewModel viewModel;
    CaptureModel[] dailyData;
    CaptureModel[] weeklyData;
    CaptureModel[] lifetimeData;
    ProgressViewModel progressViewModel;
    PreferenceViewModel preferenceViewModel;
    public ProgressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CaptureViewModel();
        viewModel.getWeeklyCapture();
        viewModel.getDailyCapture();

        progressViewModel = new ProgressViewModel();
        progressViewModel.getLifetimeDailyConsumption();

        preferenceViewModel = new PreferenceViewModel();
        preferenceViewModel.getPreferences();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        viewBinding = FragmentProgressBinding.inflate(inflater, container, false);

        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers(view);
    }

    private void initObservers(View v) {
        viewModel.liveGetDailyConsumption.observe(getViewLifecycleOwner(),
                (it) -> {
                    handelBarGraphObserver(it,viewBinding.graphViewDaily,"Daily Intake",Color.YELLOW);
                });
        viewModel.liveGetWeeklyConsumption.observe(getViewLifecycleOwner(),
                (it) -> {
                    handelBarGraphObserver(it,viewBinding.graphViewWeekly,"Weekly Intake",Color.BLUE);
                });
        progressViewModel.liveGetLifetimeDailyConsumption.observe(getViewLifecycleOwner(),
                (it)->{
                    handleLineGraphObserver(it);
                });
        preferenceViewModel.liveGetPreference.observe(getViewLifecycleOwner(),
                (it)->handleTargetLineObserver(it));
    }
    void handleTargetLineObserver(NetworkResult<PreferenceResponseModel> it)
    {
        if (it.getClass().equals((SuccessResult.class))) {
            String targetString = preferenceViewModel.liveGetPreference.getValue().getData().data.currentIntake;
            if(targetString!=null)
            {
                int target = Integer.parseInt(targetString);
                initTargetLine(target);
            }
            else
            {
                new AlertDialog.Builder(this.getContext())
                        .setTitle("Message")
                        .setMessage("Please enter target alcohol %")
                        .show();
            }
        } else if (it.getClass().equals((ErrorResult.class))) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }
    void handleLineGraphObserver(NetworkResult<CaptureResponseModel> it)
    {
        if (it.getClass().equals((SuccessResult.class))) {
            lifetimeData = progressViewModel.liveGetLifetimeDailyConsumption.getValue().getData().data;
            initLineGraph();
        } else if (it.getClass().equals((ErrorResult.class))) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }

    void handelBarGraphObserver(NetworkResult<CaptureResponseModel> it,GraphView graphView,String title,Integer barColorNumber) {
        if (it.getClass().equals((SuccessResult.class))) {
            setData();
            initGraph(graphView,title,barColorNumber);
        } else if (it.getClass().equals((ErrorResult.class))) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }
    void setData()
    {
        CaptureResponseModel dailyResponse = viewModel.liveGetDailyConsumption.getValue().getData();
        CaptureResponseModel weeklyResponse = viewModel.liveGetWeeklyConsumption.getValue().getData();
        if(dailyResponse!=null)
        {
            dailyData = dailyResponse.data;
        }
        if(weeklyResponse!=null)
        {
            weeklyData = weeklyResponse.data;
        }
    }
    void initTargetLine(int target)
    {
        DataPoint[] dp = new DataPoint[Constants.BAR_COUNT];
        for (int i = 0; i < Constants.BAR_COUNT; i++) {
            dp[i] = new DataPoint(i, target);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dp);

        viewBinding.graphViewWeekly.addSeries(series);
    }
    void initLineGraph()
    {
        graphView = viewBinding.lineGraphView;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(getDataPoint(lifetimeData));
        series.setDataPointsRadius(10);
        series.setDrawDataPoints(true);
        graphView.addSeries(series);
        graphView.setTitle("Lifetime Intake");
        graphView.setTitleColor(Color.BLACK);
        graphView.setTitleTextSize(50);
        //axis titles
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Alcohol Intake");
    }

    private void initGraph(GraphView graphView,String title,Integer barColorNumber) {
        CaptureModel[] data;
        if(title.charAt(0) == 'D')
        {
            data = dailyData;
        }
        else
        {
            data = weeklyData;
        }

        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Alcohol Intake");
        gridLabel.setHorizontalAxisTitleTextSize(40);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint(data));
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series.setSpacing(25);
        series.setColor(barColorNumber);
        graphView.addSeries(series);

        String[] dates = getDates(data);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(dates);

        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(120);
        graphView.getGridLabelRenderer().setLabelHorizontalHeight(125);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graphView.setTitle(title);
        graphView.setTitleColor(Color.BLACK);
        graphView.setTitleTextSize(50);
    }
    String[] getDates(CaptureModel[] data)
    {
        int len = data.length;
        String[] dates= new String[len];
        for(int i=0;i<len;i++)
        {
            dates[i] = data[i].date;
        }
        return  dates;
    }


    private DataPoint[] getDataPoint(CaptureModel[] data) {
        DataPoint[] dp = new DataPoint[data.length];
        for (int i = 0; i < data.length; i++) {
            dp[i] = new DataPoint(i, Double.parseDouble(data[i].drinkIntake));
        }
        return dp;
    }

    private DataPoint[] getStaticDataPoint() {
        DataPoint[] dp = new DataPoint[] {

                new DataPoint(0, 1),
                new DataPoint(1, 4),
                new DataPoint(2, 5),
                new DataPoint(3, 1),
                new DataPoint(4, 0),
                new DataPoint(5, 3),
                new DataPoint(6, 1),
                new DataPoint(7, 7),
                new DataPoint(8, 4)
        };
        return dp;
    }
}