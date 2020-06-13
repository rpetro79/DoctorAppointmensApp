package com.example.assignment.createAccountActivityANdVM;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment.R;

import java.util.Calendar;

public class PatientCreateAccount extends AppCompatActivity {
    private PatientCreateAccountViewModel viewModel;
    private TextView birthday;
    private TextView errorMessage;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_create_account);

        viewModel = new ViewModelProvider(this).get(PatientCreateAccountViewModel.class);
        birthday = findViewById(R.id.birthdayCreateAccount);
        //display the date on the screen after it is selected
        viewModel.getBirthday().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                birthday.setText(s);
            }
        });

        errorMessage = findViewById(R.id.errorMessagePatientCreateAccount);
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                errorMessage.setText(s);
            }
        });
        //set the date
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                viewModel.setDate(dayOfMonth, month, year);
            }
        };
    }

    //open the date picker dialog
    public void setBirthDate(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                dateSetListener,
                year-20, month, day);
        dialog.show();
    }

    //next step
    public void next(View view) {
        if(viewModel.nextStep())
        {
            Intent intent = new Intent(getApplicationContext(), CreateAccountStep3.class);
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
                intent.putExtra("result", "someData");
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
