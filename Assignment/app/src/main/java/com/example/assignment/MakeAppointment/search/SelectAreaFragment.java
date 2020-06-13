package com.example.assignment.MakeAppointment.search;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class SelectAreaFragment extends Fragment {
    private List<String> countries;
    private List<String> regions;
    private List<String> cities;
    private Spinner countrySpinner;
    private Spinner regionSpinner;
    private Spinner citySpinner;
    private String selectedCountry, selectedRegion, selectedCity;
    private ProgressBar progressBar;

    public SelectAreaFragment() {
        countries = new ArrayList<String>();
        regions = new ArrayList<String>();
        cities = new ArrayList<String>();
    }

    public static SelectAreaFragment newInstance() {
        SelectAreaFragment fragment = new SelectAreaFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_select_area, container, false);
        progressBar = view.findViewById(R.id.progressBarSelectArea);
        progressBar.setVisibility(View.VISIBLE);

        countrySpinner = view.findViewById(R.id.searchCountry);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = (String)parent.getItemAtPosition(position);
                ((SearchDoctorsFragment)getParentFragment()).fetchRegions(selectedCountry);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCountry = (String)parent.getItemAtPosition(0);
            }
        });

        //setting up the region spinner and the listener
        regionSpinner = view.findViewById(R.id.searchRegion);
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = (String)parent.getItemAtPosition(position);
                ((SearchDoctorsFragment)getParentFragment()).fetchCities(selectedCountry, selectedRegion);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRegion = (String)parent.getItemAtPosition(0);
            }
        });

        //setting up the city spinner and the listener
        citySpinner = view.findViewById(R.id.searchCity);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity = selectedRegion;
            }
        });
        return view;
    }


    //set the adapter for countries
    private void initializeCountrySpinner(List<String> countries)
    {
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(
                getParentFragment().getActivity(), android.R.layout.simple_spinner_item, countries
        );
        countryAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        countrySpinner.setAdapter(countryAdapter);
    }

    //set the adapter for regions based on country
    private void initializeRegionSpinner(List<String> regions)
    {
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(
                getParentFragment().getActivity(), android.R.layout.simple_spinner_item, regions
        );
        regionAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        regionSpinner.setAdapter(regionAdapter);
    }

    //set the adapter for cities based on country and region
    private void initializeCitySpinner(List<String> cities) {
        progressBar.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(
                getParentFragment().getActivity(), android.R.layout.simple_spinner_item, cities
        );
        selectedCity = selectedRegion;
        cityAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        citySpinner.setAdapter(cityAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setCountries(List<String> strings) {
        countries = strings;
        initializeCountrySpinner(countries);
    }

    public void setRegions(List<String> strings) {
        regions = strings;
        initializeRegionSpinner(regions);
    }

    public void setCities(List<String> strings) {
        cities = strings;
        initializeCitySpinner(cities);
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public String getSelectedCity() {
        return selectedCity;
    }
}
