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
    CaptureViewModel viewModel;

    Spinner spinner;
    Spinner spinner1;
    Spinner spinnerAlcohol;
    String[] alcohol_percentage = {"3 - 6%","10 - 15%","35 - 50%"};
    String[] intakemood = {"Happy", "Sad", "Angry", "Occasionally", "Nothing/Fun" };
    String[] intaketype = { "Beer: more than 7%", "Whiskey: 40% - 50%", "Wine: 5% - 25%"};
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

        viewBinding.btnCancel.setOnClickListener((v)-> Navigation.findNavController(v).popBackStack());
        viewBinding.btnSave.setOnClickListener((v)->save(v));
      
        spinner = viewBinding.spinner;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,intaketype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
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
        CaptureModel capture = new CaptureModel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            capture.date = LocalDate.now().toString();
        }
        capture.drinkName = viewBinding.spinner.getSelectedItem().toString();
        capture.drinkIntake = viewBinding.editTextQuantity.getText().toString();
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
        viewBinding.progressCircular.setVisibility(View.INVISIBLE);

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
        else
        {
            viewBinding.progressCircular.setVisibility(View.VISIBLE);
        }
    }
}