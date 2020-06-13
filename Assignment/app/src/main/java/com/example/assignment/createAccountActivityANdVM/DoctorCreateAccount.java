package com.example.assignment.createAccountActivityANdVM;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assignment.R;

import java.util.List;

public class DoctorCreateAccount extends AppCompatActivity {
    private DoctorCreateAccountViewModel viewModel;
    private String specialization;
    private EditText clinicName;
    private EditText cvr;
    private EditText address;
    private TextView errorMessage;
    private Spinner specializationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_create_account);

        viewModel = new ViewModelProvider(this).get(DoctorCreateAccountViewModel.class);
        viewModel.fetchSpecializations();
        viewModel.getSpecializations().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                initializeSpinner(strings);
            }
        });

        clinicName = findViewById(R.id.clinicNameCreateAccount);
        cvr = findViewById(R.id.cvrCreateAccount);
        address = findViewById(R.id.addressCreateAccount);
        errorMessage = findViewById(R.id.errorMessageDoctorCreateAccount);

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                errorMessage.setText(s);
            }
        });

        //set the spinner and the listener
        specializationSpinner = findViewById(R.id.specializationCreateAccount);

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
    }

    private void initializeSpinner(List<String> strings) {
        ArrayAdapter<String> specializationAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, strings
        );
        specializationAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        specializationSpinner.setAdapter(specializationAdapter);
    }

    //intent to the next step
    public void next(View view) {
        if(viewModel.nextStep(clinicName.getText().toString(), cvr.getText().toString(), address.getText().toString(), specialization))
        {
            Intent intent = new Intent(getApplicationContext(), AddDAT.class);
            startActivityForResult(intent, 3);
        }
    }

    //wait until the account is created to finish the activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3)
        {
            if(resultCode == RESULT_OK)
            {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
    }
}
