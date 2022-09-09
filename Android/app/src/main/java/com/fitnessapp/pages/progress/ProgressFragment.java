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
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ProgressFragment extends Fragment {
    GraphView graphView;
    GraphView graphView1;
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
        initGraph1();
        initAxisTiles();
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

    private void initGraph1()
    {
        //graph initializing
        graphView1=(GraphView) viewBinding.idGraphView;
        //  final DateFormat dateTimeFormatter = DateFormat.getDateTimeInstance();
        //graphView = new BarGraphSeries<DataPoint>(context,"chart");

        //graphView = viewBinding.graph;
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(getDataPoint1()) ;
        graphView1.addSeries(series);
        series.setDrawValuesOnTop( true ) ;
        series.setValuesOnTopColor( Color.RED ) ;
        series.setSpacing( 40 ) ;
        graphView1.setTitle("Daily Intake");

        graphView1.setTitleColor(Color.BLACK);

        graphView1.setTitleTextSize(50);
    }

    private void initAxisTiles()
    {
        GridLabelRenderer gridLabel = graphView1.getGridLabelRenderer();
        gridLabel.setVerticalAxisTitle("Intake(in ML)");
        gridLabel.setHorizontalAxisTitleTextSize(40);
    }
    private DataPoint[] getDataPoint1() {
        DataPoint[] dp = new DataPoint[]
                {

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