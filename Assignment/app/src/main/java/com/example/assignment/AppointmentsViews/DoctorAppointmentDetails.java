package com.example.assignment.AppointmentsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.assignment.DoctorViews.DoctorViewsViewModel;
import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.R;
import com.example.assignment.Shared.DateTimeFormat;
import com.example.assignment.localDb.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DoctorAppointmentDetails extends AppCompatActivity {
    private DoctorAppointment appointment;
    private TextView topLabel;
    private TextView age;
    private TextView symptoms;
    private TextView cancelled;
    private TextView patientEmail;
    private TextView patientPhone;
    private TextView dateTime;
    private DoctorAppointmentDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_details);

        Toolbar toolbar = findViewById(R.id.myToolbarDoctorViews);
        setSupportActionBar(toolbar);

        viewModel = new ViewModelProvider(this).get(DoctorAppointmentDetailsViewModel.class);

        Intent intent = getIntent();
        appointment = (DoctorAppointment) intent.getSerializableExtra("appointment");
        viewModel.setAppointment(appointment);

        viewModel.getAppointment().observe(this, new Observer<DoctorAppointment>() {
            @Override
            public void onChanged(DoctorAppointment doctorAppointment) {
                topLabel.setText(appointment.getPatient().getName());
                Calendar cal = Calendar.getInstance();
                Date d = new Date(cal.get(Calendar.YEAR)-1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                age.setText(String.format(getResources().getString(R.string.ageText), appointment.getPatient().calculateAge(d)));
                symptoms.setText(String.format(getResources().getString(R.string.symptomsText), appointment.getSymptoms()));
                patientEmail.setText(String.format(getResources().getString(R.string.emailText), appointment.getPatient().getEmail()));
                patientPhone.setText(String.format(getResources().getString(R.string.phoneText), appointment.getPatient().getPhone()));
                dateTime.setText(DateTimeFormat.formatDateTime(appointment.getDatetime()));

                if(appointment.isCancelled())
                    cancelled.setText(R.string.cancelled);
            }
        });

        topLabel = findViewById(R.id.labelDoctorAppointmentDetails);
        age = findViewById(R.id.ageDoctorAppointmentDetails);
        symptoms = findViewById(R.id.symptomsDoctorAppointmentDetails);
        cancelled = findViewById(R.id.cancelledDoctorAppointmentDetails);
        patientEmail = findViewById(R.id.patientEmailDoctorAppointmentDetails);
        patientPhone = findViewById(R.id.patientPhoneDoctorAppointmentDetails);
        dateTime = findViewById(R.id.dateTimeOfAppointmentDoctorAppointmentDetails);

        viewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users == null || users.size() == 0) {
                    finish();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_button, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_button: {
                viewModel.logOut();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
