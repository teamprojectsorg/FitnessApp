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
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.fitnessapp.databinding.FragmentCaptureBinding;
import com.fitnessapp.models.ApiResponseModel;
import com.fitnessapp.network.NetworkResult;
import com.fitnessapp.network.results.ErrorResult;
import com.fitnessapp.network.results.SuccessResult;
import com.fitnessapp.pages.capture.models.CaptureModel;

import java.time.LocalDate;

public class CaptureFragment extends Fragment {

    Spinner spinnerAlcohol, quantitySpinner;
    CaptureViewModel viewModel;
    String[] actualAlcoholPercent = {"6","50","15"};
    String[] alcohol_percentage = {"3 - "+actualAlcoholPercent[0],
            "35 - "+actualAlcoholPercent[1],
            "10 - "+actualAlcoholPercent[2]};
    String[] intakemood = {"Happy", "Sad", "Angry", "Occasionally", "Nothing/Fun" };
    String[] intaketype = { "Beer", "Whiskey", "Wine"};
    int[] beerMl = {285,375,425};
    String[] beerQuantity = {"small glass ("+beerMl[0]+"ml)",
            "can/bottle ("+beerMl[1]+"ml)", "large glass ("+beerMl[2]+"ml)"};
    int[] whiskyMl = {30,60,90};
    String[] whiskyQuantity = {"small ("+whiskyMl[0]+"ml)",
            "medium ("+whiskyMl[1]+"ml)", "large ("+whiskyMl[2]+"ml)"};
    int[] wineMl = {100,150};
    String[] wineQuantity = {"standard serving ("+wineMl[0]+"ml)",
            "restaurant serving ("+wineMl[1]+"ml)"};
    int[] selectedMl;
    int selectedIntakeType;
    int selectedEmotion;
    int selectedAlcoholPercentage;

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
        setListners();
    }
    void setListners()
    {
        viewBinding.alcoholGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                viewBinding.dropdownParentLayout.setVisibility(View.VISIBLE);
                int checkedRadioButton = viewBinding.alcoholGroup.getCheckedRadioButtonId();
                if(checkedRadioButton == viewBinding.alcoholBeer.getId())
                {
                    selectedAlcoholPercentage = 0;
                    selectedIntakeType = 0;
                    selectedMl = beerMl;
                    setAlcoholAdapter(beerQuantity);
                }
                else if(checkedRadioButton == viewBinding.alcoholWhisky.getId()) {
                    selectedAlcoholPercentage = 1;
                    selectedIntakeType = 1;
                    selectedMl = whiskyMl;
                    setAlcoholAdapter(whiskyQuantity);
                }
                else if(checkedRadioButton == viewBinding.alcoholWine.getId())
                {
                    selectedAlcoholPercentage = 2;
                    selectedIntakeType = 2;
                    selectedMl = wineMl;
                    setAlcoholAdapter(wineQuantity);
                }

                ArrayAdapter<String> adapterAlcohol = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item,
                        new String[]{alcohol_percentage[selectedAlcoholPercentage]});
                adapterAlcohol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAlcohol.setAdapter(adapterAlcohol);
            }
        });
        viewBinding.emotionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRadioButton = viewBinding.emotionGroup.getCheckedRadioButtonId();
                if(checkedRadioButton == viewBinding.emotionHappy.getId())
                {selectedEmotion=0;}
                else if(checkedRadioButton == viewBinding.emotionSad.getId())
                {selectedEmotion=1;}
                else if(checkedRadioButton == viewBinding.emotionAngry.getId())
                {selectedEmotion=2;}
                else if(checkedRadioButton == viewBinding.emotionOccasionally.getId())
                {selectedEmotion=3;}
            }
        });
    }
    void setAlcoholAdapter(String[] data)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item , data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quantitySpinner.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentCaptureBinding.inflate(inflater,container,false);

        viewBinding.btnSubmit.setOnClickListener((v)->save(v));

        //for quantity
        quantitySpinner = viewBinding.spinnerQuantity;
        //for alcohol percentage
        spinnerAlcohol = viewBinding.spinnerAlcoholPercentage;

        //type of alcohol spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,intaketype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dependent spinner on type - whisky spinner
        ArrayAdapter<String> adapterQuantityWhisky = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,whiskyQuantity);
        adapterQuantityWhisky.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dependent spinner on type - wine spinner
        ArrayAdapter<String> adapterQuantityWine = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,wineQuantity);
        adapterQuantityWine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dependent spinner on type - beer alcohol percentage
        ArrayAdapter<String> adapterAlcohol = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,alcohol_percentage);
        adapterAlcohol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Item selected listener on type of alcohol
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,intakemood);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return viewBinding.getRoot();
    }

    public void save(View v)
    {
        CaptureModel capture = new CaptureModel();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            capture.date = LocalDate.now().toString();
        }
        capture.drinkName = intaketype[selectedIntakeType];

        capture.drinkIntake = getDrinkIntake();
        capture.alcoholPercentage = actualAlcoholPercent[selectedAlcoholPercentage];
        capture.drinkIntension = intakemood[selectedEmotion];
        viewModel.addCapture(capture);
    }
    String getAlcoholPercent()
    {
        int selectedItem = viewBinding.spinnerAlcoholPercentage.getSelectedItemPosition();
        return actualAlcoholPercent[selectedItem];
    }
    String getDrinkIntake()
    {
        int selectedItem  =viewBinding.spinnerQuantity.getSelectedItemPosition();
        return String.valueOf(selectedMl[selectedItem]);
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