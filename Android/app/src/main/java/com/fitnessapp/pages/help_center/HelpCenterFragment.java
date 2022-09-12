package com.fitnessapp.pages.help_center;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnessapp.R;

import com.fitnessapp.activities.MainActivity;
import com.fitnessapp.databinding.FragmentHelpCenterBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelpCenterFragment extends Fragment implements OnMapReadyCallback {
    static Context context;
    private static final float DEFAULT_ZOOM = 9;
    String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=50000&keyword=rehabcenter&key=AIzaSyDm6g2QPpdL8lKkJ1tlO20CHCDCGxbk66Y&";
    //GoogleMap map;
    FragmentHelpCenterBinding viewBinding;
    FusedLocationProviderClient client;
    LatLng currentLatLng;
    GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHelpCenterBinding.inflate(inflater, container, false);

        //if(isServicesOk()) {
        //initMap();
        //}
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    private void geoLocate() {
        String searchString = "rehab centers near me";
        Geocoder geoCoder = new Geocoder(this.getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geoCoder.getFromLocationName(searchString, 7);
        }catch (IOException e) {}
        if(list.size() > 0) {
          Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(),
                    address.getLongitude()),
                    DEFAULT_ZOOM);
        }
    }

    private void getDeviceLocation() {
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            Task locationTask = client.getLastLocation();
            locationTask.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    Location location = (Location) locationTask.getResult();
                    currentLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                    moveCamera(currentLatLng,
                            DEFAULT_ZOOM);
                    sendApiRequest(currentLatLng);
                }
            });

        } catch (SecurityException e) {
        }
    }
void sendApiRequest(LatLng currentLocation)
{
    String finalUrl = URL + "location=" + currentLocation.latitude +
            "," + currentLocation.longitude;

    RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
    JsonObjectRequest objectRequest = new JsonObjectRequest(
            Request.Method.GET, finalUrl, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    LocationResponseModel locations = gson.fromJson(String.valueOf(response),
                            LocationResponseModel.class);
                    plotLocations(locations);
                    moveCamera(currentLatLng,DEFAULT_ZOOM);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage(error.getMessage())
                            .show();
                }
            }
    );
    requestQueue.add(objectRequest);
}
void plotLocations(LocationResponseModel locations)
{
    for(int i = 0;i<locations.results.size();i++)
    {
        LocationResponseModel.Result result = locations.results.get(i);
        LocationResponseModel.Location location = result.geometry.location;
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.lat,location.lng))
                .title(result.name)
                );
        marker.showInfoWindow();
    }
}
    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera (CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options =  new MarkerOptions().position(latLng);
        mMap.addMarker(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage("Please enable location permission")
                    .show();
            return;
        }
        getDeviceLocation();
        mMap.setMyLocationEnabled(true);
    }
   /*

    private void init() {

    }
    public boolean isServicesOk() {
        Log.d(TAG, "isServicesOk : checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.getContext());
        if(available == ConnectionResult.SUCCESS) {
            Log.d(TAG, "isServicesOk : Google play services are working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK : an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HelpCenterFragment.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();;
        }
        else {
            Toast.makeText(this.getContext(), "You can't make map requests", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,"You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    } */
}