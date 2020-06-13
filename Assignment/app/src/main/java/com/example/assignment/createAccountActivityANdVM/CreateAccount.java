package com.example.assignment.createAccountActivityANdVM;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.Model.UserType;
import com.example.assignment.R;
import com.example.assignment.firstPageAndLogIn.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity {
    private EditText ssn;
    private EditText name;
    private EditText phone;
    private EditText email;
    private Spinner countrySpinner;
    private Spinner regionSpinner;
    private Spinner citySpinner;
    private String country, region, city;
    private RadioGroup type;
    private TextView errorMessage;
    private CreateAccountViewModel viewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        ssn = findViewById(R.id.ssnCreateAccount);
        name = findViewById((R.id.name));
        phone = findViewById(R.id.phoneNo);
        email = findViewById(R.id.email);
        type = findViewById(R.id.type);
        progressBar = findViewById(R.id.progressBarCreateAccount);

        viewModel.getCountries().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initializeCountrySpinner(strings);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getRegions().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initializeRegionSpinner(strings);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getCities().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initializeCitySpinner(strings);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        //setting up the country spinner and the listener
        viewModel.fetchCountries();

        countrySpinner = findViewById(R.id.country);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = (String)parent.getItemAtPosition(position);
                viewModel.fetchRegions(country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                country = (String)parent.getItemAtPosition(0);
            }
        });

        //setting up the region spinner and the listener
        regionSpinner = findViewById(R.id.region);
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = (String)parent.getItemAtPosition(position);
                viewModel.fetchCities(country, region);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                region = (String)parent.getItemAtPosition(0);
            }
        });

        //setting up the city spinner and the listener
        citySpinner = findViewById(R.id.city);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city = region;
            }
        });

        errorMessage = findViewById(R.id.createAccountStep1Error);

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                errorMessage.setText(s);
            }
        });
    }

    //set the adapter for countries
    private void initializeCountrySpinner(List<String> countries)
    {
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, countries
        );
        countryAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        countrySpinner.setAdapter(countryAdapter);
    }

    //set the adapter for regions based on country
    private void initializeRegionSpinner(List<String> regions)
    {
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, regions
        );
        regionAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        regionSpinner.setAdapter(regionAdapter);
    }

    //set the adapter for cities based on country and region
    private void initializeCitySpinner(List<String> cities) {
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, cities
        );
        city = region;
        cityAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        citySpinner.setAdapter(cityAdapter);
    }


    //intent to next step
    public void next(View view) {
        switch(type.getCheckedRadioButtonId())
        {
            case R.id.patientRadioButton:
            {
                if(viewModel.secondStep(ssn.getText().toString(), name.getText().toString(), phone.getText().toString(), email.getText().toString(), country, city, UserType.PATIENT))
                {
                    Intent intent = new Intent(getApplicationContext(), PatientCreateAccount.class);
                    startActivityForResult(intent, 2);
                }
                break;
            }
            case R.id.doctorRadioButton:
                if(viewModel.secondStep(ssn.getText().toString(), name.getText().toString(), phone.getText().toString(), email.getText().toString(), country, city, UserType.DOCTOR))
                {
                    Intent intent = new Intent(getApplicationContext(), DoctorCreateAccount.class);
                    startActivityForResult(intent, 2);
                }
                break;
        }
    }

    //wait until the account is created to finish the activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2)
        {
            if(resultCode == RESULT_OK)
                finish();
        }
    }
}
