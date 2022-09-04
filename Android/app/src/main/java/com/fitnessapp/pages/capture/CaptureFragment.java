package com.fitnessapp.pages.capture;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitnessapp.R;
import com.fitnessapp.databinding.FragmentCaptureBinding;
import com.fitnessapp.databinding.FragmentSignupBinding;

public class CaptureFragment extends Fragment {
    Spinner spinner;
    Spinner spinner1;
    String[] intakemood = {"Happy", "Sad", "Angry", "Occasionally", "Nothing/Fun" };
    String[] intaketype = {"Alcohol", "Beer", "Whiskey"};
    FragmentCaptureBinding viewBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentCaptureBinding.inflate(inflater,container,false);

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
        spinner1 =
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

        return viewBinding.getRoot();
    }
}