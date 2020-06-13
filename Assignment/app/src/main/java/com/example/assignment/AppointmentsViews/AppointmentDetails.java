package com.example.assignment.AppointmentsViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.R;
import com.example.assignment.localDb.User;

import java.util.Calendar;
import java.util.List;

public class AppointmentDetails extends AppCompatActivity  implements AppointmentCancellationConfirmationDialog.AppointmentCancellationConfirmationDialogListener{
    PatientAppointment appointment;
    AppointmentDetailsViewModel viewModel;
    AppointmentDetailsFragment adf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        viewModel = new ViewModelProvider(this).get(AppointmentDetailsViewModel.class);

        adf = AppointmentDetailsFragment.newInstance(appointment);

        Toolbar toolbar = findViewById(R.id.myToolbarAppointments);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        appointment = (PatientAppointment) intent.getSerializableExtra("appointment");
        viewModel.setAppointment(appointment);

        viewModel.getAppointment().observe(this, new Observer<PatientAppointment>() {
            @Override
            public void onChanged(PatientAppointment patientAppointment) {
                if(adf != null)
                {
                    adf.setAppointment(patientAppointment);
                }
            }
        });

        viewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users == null || users.size() == 0) {
                    finish();
                }
            }
        });

        displayFragment(adf, R.id.appointmentContainer);

        if(appointment.getDatetime().getTime() > Calendar.getInstance().getTimeInMillis() && !appointment.isCancelled())
            displayFragment(AppointmentControlsFragment.newInstance(), R.id.appointmentControlsContainer);
    }

    //puts the options in the toolbar (only log out)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_button, menu);
        return true;
    }

    public void changeFragment(Fragment f, int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(resId, f);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void displayFragment(Fragment f, int resId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(resId,
                f);
        fragmentTransaction.commit();
    }

    public void closeFragment(Fragment f) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.remove(f).commit();
    }

    @Override
    public void cancel() {
        viewModel.cancelAppointment();
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
