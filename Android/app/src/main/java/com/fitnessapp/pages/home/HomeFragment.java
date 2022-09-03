package com.fitnessapp.pages.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.databinding.FragmentHomeBinding;
import com.jjoe64.graphview.GraphView;
import com.ramotion.circlemenu.CircleMenuView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class HomeFragment extends Fragment {

    GraphView graphView;
    FragmentHomeBinding viewBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHomeBinding.inflate(inflater,container,false);

        initGraph();
        initAxisTiles();
        initCircleMenu();

        return viewBinding.getRoot();
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

    private void initAxisTiles()
    {
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("No. of Males");
        gridLabel.setHorizontalAxisTitleTextSize(40);
        gridLabel.setVerticalAxisTitleTextSize(40);
        gridLabel.setVerticalAxisTitle("No. of Males");
    }

    private void initGraph()
    {
        //graph initializing
        graphView = viewBinding.idGraphView;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(8, 2)
        });
        graphView.setTitle("Average Alcohol Intake of Males and Females ");

        graphView.setTitleColor(Color.BLACK);

        graphView.setTitleTextSize(30);

        graphView.addSeries(series);
        series.setColor(Color.BLUE);
        series.setTitle("Males in ths");
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }
}