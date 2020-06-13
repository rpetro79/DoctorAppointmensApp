package com.example.assignment.DoctorViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.AppointmentsViews.AppointmentDetails;
import com.example.assignment.AppointmentsViews.AppointmentFragmentActivityInterface;
import com.example.assignment.AppointmentsViews.AppointmentListType;
import com.example.assignment.AppointmentsViews.AppointmentsFragment;
import com.example.assignment.AppointmentsViews.DoctorAppointmentDetails;
import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Model.UserType;
import com.example.assignment.R;
import com.example.assignment.WelcomePage.WelcomeFragment;
import com.example.assignment.WelcomePage.WelcomeViewModel;
import com.example.assignment.firstPageAndLogIn.MainActivity;
import com.example.assignment.localDb.AppointmentDB;
import com.example.assignment.localDb.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DoctorViews extends AppCompatActivity implements AppointmentFragmentActivityInterface {
    private DoctorViewsViewModel viewModel;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView dateText;
    private AppointmentsFragment appointmentsFragment;
    private List<AppointmentDB> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_views);

        viewModel = new ViewModelProvider(this).get(DoctorViewsViewModel.class);

        Toolbar toolbar = findViewById(R.id.myToolbarDoctorViews);
        setSupportActionBar(toolbar);

        dateText = findViewById(R.id.dateTextDoctorViews);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                viewModel.setDate(year, month, dayOfMonth);
            }
        };

        viewModel.getDateString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dateText.setText(s);
            }
        });

        viewModel.getAppointments().observe(this, new Observer<List<AppointmentDB>>() {
            @Override
            public void onChanged(List<AppointmentDB> appts) {
                appointments = appts;
                if(appointmentsFragment == null)
                {
                    appointmentsFragment = AppointmentsFragment.newInstance();
                    appointmentsFragment.setData(appointments);
                    appointmentsFragment.setAppointmentsType(AppointmentListType.DAY);
                    displayFragment(appointmentsFragment);
                    if(appointments != null && appointments.size() > 0)
                    {
                        viewModel.setDate(appointments.get(0).getDate());
                    }
                }
                else appointmentsFragment.refresh(appointments);
            }
        });

        viewModel.getAppointment().observe(this, new Observer<DoctorAppointment>() {
            @Override
            public void onChanged(DoctorAppointment doctorAppointment) {
                Intent intent = new Intent(getApplicationContext(), DoctorAppointmentDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("appointment", doctorAppointment);
                intent.putExtras(bundle);
                startActivity(intent);
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

        viewModel.getCannotFetchAppointment().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true)
                {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.cannotFetchAppointments), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_button, menu);
        return true;
    }

    public void setDateDoctorViews(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                dateSetListener,
                year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() + 1800000);
        dialog.show();
    }

    public void changeFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.appointmentsListDoctor, f);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void displayFragment(Fragment f) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.appointmentsListDoctor,
                f);
        fragmentTransaction.commit();
    }

    @Override
    public List<AppointmentDB> getUpcomingAppointments() {
        return null;
    }

    @Override
    public List<AppointmentDB> getPastAppointments() {
        return null;
    }

    @Override
    public List<AppointmentDB> getDaysAppointments() {
        return appointments;
    }

    @Override
    public void getAppointmentDetails(AppointmentDB appointmentDB) {
        viewModel.getAppointentDetails(appointmentDB.getId());
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
