package com.example.assignment.MakeAppointment.search;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.assignment.MakeAppointment.SearchDoctors;
import com.example.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class SearchDoctorsFragment extends Fragment {
    private Spinner specializationSpinner;
    private String specialization, country, city;
    private RadioGroup radioGroup;
    private boolean selectedAreaFragmentIsOn;
    private SelectAreaFragment selectAreaFragment;
    private List<String> specializations;

    public SearchDoctorsFragment() {
        // Required empty public constructor
    }

    public static SearchDoctorsFragment newInstance() {
        SearchDoctorsFragment fragment = new SearchDoctorsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((SearchDoctors)getActivity()).fetchSpecializations();
        View view = inflater.inflate(R.layout.fragment_search_doctors, container, false);
        specializationSpinner = view.findViewById(R.id.searchSpecializationSpinner);

        specializationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specialization = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                specialization = (String)parent.getItemAtPosition(0);
            }
        });

        radioGroup = view.findViewById(R.id.searchArea);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                    {
                        if(selectAreaFragment != null)
                        {
                            closeFragment(selectAreaFragment);
                            selectedAreaFragmentIsOn = false;
                        }
                        break;
                    }
                    case 1:
                    {
                        ((SearchDoctors)getActivity()).fetchCountries();
                        selectAreaFragment = new SelectAreaFragment();
                        displayFragment(selectAreaFragment);
                        selectedAreaFragmentIsOn = true;
                        break;
                    }
                    default:
                        break;
                }
            }
        });
        return view;
    }

    public void initializeSpinner(List<String> strings) {
        specializations = strings;
        ArrayAdapter<String> specializationAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, specializations
        );
        specializationAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        specializationSpinner.setAdapter(specializationAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void displayFragment(Fragment f) {
        FragmentManager fragmentManager = getChildFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.selectAreaFragment,
                f);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void closeFragment(Fragment f) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.remove(f).commit();
    }

    public void fetchRegions(String selectedCountry) {
        ((SearchDoctors)getActivity()).fetchRegions(selectedCountry);
    }

    public void fetchCities(String selectedCountry, String selectedRegion) {
        ((SearchDoctors)getActivity()).fetchCities(selectedCountry, selectedRegion);

    }

    public void gotCountries(List<String> strings) {
        if(selectedAreaFragmentIsOn)
            selectAreaFragment.setCountries(strings);
    }

    public void gotRegions(List<String> strings) {
        if(selectedAreaFragmentIsOn)
            selectAreaFragment.setRegions(strings);
    }

    public void gotCities(List<String> strings) {
        if(selectedAreaFragmentIsOn)
            selectAreaFragment.setCities(strings);
    }

    public int getCheckedRadioButtonId() {
        return radioGroup.getCheckedRadioButtonId();
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getSelectedCountry()
    {
        return selectAreaFragment.getSelectedCountry();
    }

    public String getSelectedCity()
    {
        return selectAreaFragment.getSelectedCity();
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
