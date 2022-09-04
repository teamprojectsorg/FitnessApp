package com.fitnessapp.pages.progress;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentHomeBinding;
import com.fitnessapp.databinding.FragmentProgressBinding;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ProgressFragment extends Fragment {
    GraphView graphView;
    FragmentProgressBinding viewBinding;

    public ProgressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentProgressBinding.inflate(inflater,container,false);
        initGraph();
        return viewBinding.getRoot();
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
        DataPoint[] dp = new DataPoint[]
                {

                        new DataPoint(0, 4),
                        new DataPoint(1, 5),
                        new DataPoint(2, 1),
                        new DataPoint(3, 7),
                        new DataPoint(4, 4),
                        new DataPoint(5, 1),
                        new DataPoint(6, 5),
                        new DataPoint(7, 2),
                        new DataPoint(8, 4)
                };
        return dp;
    }
}