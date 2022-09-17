package com.fitnessapp.pages.progress;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;

public class CustomLegendRenderer extends LegendRenderer {
    /**
     * creates legend renderer
     *
     * @param graphView regarding graphview
     */
    Series blankSeries;
    GraphView mGraphView;
    public CustomLegendRenderer(GraphView graphView,Series blankSeries) {
        super(graphView);
        this.blankSeries = blankSeries;
        this.mGraphView = graphView;
    }

    @Override
    protected List<Series> getAllSeries() {
        List<Series> allSeries = new ArrayList<Series>();
        allSeries.addAll(mGraphView.getSeries());
        allSeries.remove(blankSeries);
        if (mGraphView.getSecondScale() != null) {
            allSeries.addAll(mGraphView.getSecondScale().getSeries());
        }
        return allSeries;
    }
}
