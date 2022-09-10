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
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ProgressFragment extends Fragment {
    FragmentProgressBinding viewBinding;

    CaptureViewModel viewModel;
    CaptureModel[] dailyData;
    CaptureModel[] weeklyData;

    public ProgressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CaptureViewModel();
        viewModel.getWeeklyCapture();
        viewModel.getDailyCapture();
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
                    handelObserver(it,viewBinding.graphViewDaily,"Daily Intake",Color.YELLOW);
                });
        viewModel.liveGetWeeklyConsumption.observe(getViewLifecycleOwner(),
                (it) -> {
                    handelObserver(it,viewBinding.graphViewWeekly,"Weekly Intake",Color.BLUE);
                });
    }

    void handelObserver(NetworkResult<CaptureResponseModel> it,GraphView graphView,String title,Integer barColorNumber) {
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
            setUsersWeeklyIntake();
        }
    }
    void setUsersWeeklyIntake()
    {
        if(weeklyData!=null)
        {
            CaptureModel[] data = weeklyData;
            viewBinding.lblUserIntake.setText(data[data.length - 1].drinkIntake);
        }
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
        gridLabel.setVerticalAxisTitle("Intake(in ML)");
        gridLabel.setHorizontalAxisTitleTextSize(40);

        graphView.removeAllSeries();
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint(data));
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        series.setSpacing(40);
        series.setColor(barColorNumber);
        graphView.addSeries(series);

        graphView.setTitle(title);
        graphView.setTitleColor(Color.BLACK);
        graphView.setTitleTextSize(50);
    }

    private DataPoint[] getDataPoint(CaptureModel[] data) {
        DataPoint[] dp = new DataPoint[data.length];
        for (int i = 0; i < data.length; i++) {
            dp[i] = new DataPoint(i, Integer.parseInt(data[i].drinkIntake));
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