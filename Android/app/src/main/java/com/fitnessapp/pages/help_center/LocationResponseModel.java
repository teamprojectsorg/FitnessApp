package com.fitnessapp.pages.help_center;

import java.util.ArrayList;

public class LocationResponseModel {
    public ArrayList<Result> results;

    public class Geometry{
        public Location location;
    }

    public class Location{
        public double lat;
        public double lng;
    }

    public class Result{
        public Geometry geometry;
        public String name;
        public String place_id;
    }

}
