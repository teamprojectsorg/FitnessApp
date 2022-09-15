package com.fitnessapp.pages.help_center;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
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

import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelpCenterFragment extends Fragment {
    static Context context;
    FragmentHelpCenterBinding viewBinding;
    TextView community_alcoholism, community_science, community_stopDrinking,community_addiction,
            article_alcohol, article_standardDrinks, article_laws, article_quitAlcohol,
             contact_adf, contact_ata, contact_aaac, contact_dl,
             tel_contact_adf, tel_contact_ata, tel_contact_aaa, tel_contact_dl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentHelpCenterBinding.inflate(inflater, container, false);

        community_alcoholism = viewBinding.alcoholCommunity;
        community_alcoholism.setMovementMethod(LinkMovementMethod.getInstance());

        community_science = viewBinding.scienceCommunity;
        community_science.setMovementMethod(LinkMovementMethod.getInstance());

        community_stopDrinking = viewBinding.stopDrinkingCommunity;
        community_stopDrinking.setMovementMethod(LinkMovementMethod.getInstance());

        community_addiction = viewBinding.addictionCommunity;
        community_addiction.setMovementMethod(LinkMovementMethod.getInstance());

        article_alcohol = viewBinding.alcoholArticle;
        article_alcohol.setMovementMethod(LinkMovementMethod.getInstance());

        article_standardDrinks = viewBinding.standardArticle;
        article_standardDrinks.setMovementMethod(LinkMovementMethod.getInstance());

        article_laws = viewBinding.lawsArticle;
        article_laws.setMovementMethod(LinkMovementMethod.getInstance());

        article_quitAlcohol = viewBinding.quitArticle;
        article_quitAlcohol.setMovementMethod(LinkMovementMethod.getInstance());

        contact_adf = viewBinding.adfContact;
        contact_adf.setMovementMethod(LinkMovementMethod.getInstance());

        contact_ata = viewBinding.ataContact;
        contact_ata.setMovementMethod(LinkMovementMethod.getInstance());

        contact_aaac = viewBinding.aaaContact;
        contact_aaac.setMovementMethod(LinkMovementMethod.getInstance());

        contact_dl = viewBinding.dlContact;
        contact_dl.setMovementMethod(LinkMovementMethod.getInstance());

        tel_contact_adf = viewBinding.adfTelContact;
        tel_contact_adf.setMovementMethod(LinkMovementMethod.getInstance());

        tel_contact_ata = viewBinding.ataTelContact;
        tel_contact_ata.setMovementMethod(LinkMovementMethod.getInstance());

        tel_contact_aaa = viewBinding.aaaTelContact;
        tel_contact_aaa.setMovementMethod(LinkMovementMethod.getInstance());

        contact_dl = viewBinding.dlContact;
        contact_dl.setMovementMethod(LinkMovementMethod.getInstance());
        //if(isServicesOk()) {
        //initMap();
        //}
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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