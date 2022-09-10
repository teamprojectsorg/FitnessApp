package com.fitnessapp.pages.capture;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fitnessapp.databinding.FragmentCaptureBinding;
import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.models.CaptureModel;

import java.time.LocalDate;

public class CaptureFragment extends Fragment {

    Spinner spinner , spinner1, spinnerAlcohol, quantitySpinner;
    CaptureViewModel viewModel;
    String[] alcohol_percentage = {"3 - 6%","10 - 15%","35 - 50%"};
    String[] intakemood = {"Happy", "Sad", "Angry", "Occasionally", "Nothing/Fun" };
    String[] intaketype = { "Beer", "Whiskey", "Wine"};
    String[] beerQuantity = {"285ml", "375ml", "425ml"};
    String[] whiskyQuantity = {"30ml", "275ml", "700ml"};
    String[] wineQuantity = {"100ml", "150ml", "750ml"};

    FragmentCaptureBinding viewBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new CaptureViewModel();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindObserver(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentCaptureBinding.inflate(inflater,container,false);

        viewBinding.btnSubmit.setOnClickListener((v)->save(v));
      
        spinner = viewBinding.spinner;
        quantitySpinner = viewBinding.spinnerQuantity;
        //type of alcohol spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,intaketype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //dependent spinner on type - beer quantity spinner
        ArrayAdapter<String> adapterQuantityBeer = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item , beerQuantity);
        adapterQuantityBeer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dependent spinner on type - whisky spinner
        ArrayAdapter<String> adapterQuantityWhisky = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,whiskyQuantity);
        adapterQuantityWhisky.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dependent spinner on type - wine spinner
        ArrayAdapter<String> adapterQuantityWine = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,wineQuantity);
        adapterQuantityWine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Item selected listener on type of alcohol
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                if(position==0) {
                    quantitySpinner.setAdapter(adapterQuantityBeer);
                    quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String value1 = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else if(position==1) {
                    quantitySpinner.setAdapter(adapterQuantityWhisky);
                    quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String value2 = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else{
                    quantitySpinner.setAdapter(adapterQuantityWine);
                    quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String value3 = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //for intake mood
        spinner1 = viewBinding.spinnerwhy;

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,intakemood);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //for alcohol percentage
        spinnerAlcohol = viewBinding.spinnerAlcoholPercentage;

        ArrayAdapter<String> adapterAlcohol = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,alcohol_percentage);
        adapterAlcohol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlcohol.setAdapter(adapterAlcohol);

        spinnerAlcohol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String valueAlcohol = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return viewBinding.getRoot();
    }

    public void save(View v)
    {
        //TODO Handel dropdowns
        CaptureModel capture = new CaptureModel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            capture.date = LocalDate.now().toString();
        }
        capture.drinkName = viewBinding.spinner.getSelectedItem().toString();
        //capture.drinkIntake = viewBinding.editTextQuantity.getText().toString();
        capture.drinkIntension = viewBinding.spinnerwhy.getSelectedItem().toString();
        viewModel.addCapture(capture);
    }
    public void cancel(View v)
    {
        Navigation.findNavController(viewBinding.getRoot()).popBackStack();
    }

    private void bindObserver(View view)
    {
        viewModel.postResponse.observe(getViewLifecycleOwner(),
                (it) ->handleObserver(it));
    }

    void handleObserver(NetworkResult<ApiResponseModel> it)
    {
        if(it.getClass().equals((SuccessResult.class)))
        {
            NavController navController = Navigation.findNavController(viewBinding.getRoot());
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Success")
                    .setMessage("Data captured")
                    .show();
            navController
                    .popBackStack();
        }
        else if(it.getClass().equals((ErrorResult.class)))
        {
            new AlertDialog.Builder(this.getContext())
                    .setTitle("Error")
                    .setMessage(it.getMessage())
                    .show();
        }
    }
}