package com.example.assignment.WelcomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.assignment.AppointmentsViews.AppointmentDetails;
import com.example.assignment.AppointmentsViews.AppointmentFragmentActivityInterface;
import com.example.assignment.AppointmentsViews.AppointmentListType;
import com.example.assignment.AppointmentsViews.AppointmentsFragment;
import com.example.assignment.DoctorViews.DoctorViews;
import com.example.assignment.MakeAppointment.SearchDoctors;
import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.Model.UserType;
import com.example.assignment.R;
import com.example.assignment.firstPageAndLogIn.MainActivity;
import com.example.assignment.localDb.AppointmentDB;
import com.example.assignment.localDb.User;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Welcome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AppointmentFragmentActivityInterface {

    private WelcomeViewModel viewModel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private List<AppointmentDB> activeAppointments;
    private List<AppointmentDB> pastAppointments;
    private String username;
    private AppointmentsFragment appointmentsFragment;
    private WelcomeFragment welcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);

        //sets the toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        //sets up the drawer layout
        drawerLayout = findViewById(R.id.welcome_page_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //sets up the navigation options based on the user type
        navigationView = findViewById(R.id.navigationView);

        viewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users == null || users.size() == 0) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    username = users.get(0).getName();
                    welcomeFragment = WelcomeFragment.newInstance(username);
                    if (users.get(0).getUserType() == UserType.PATIENT) {
                        navigationView.inflateMenu(R.menu.patient_drawer);
                        welcomeFragment.setFabVisibility(true);
                    }
                    else {
                        navigationView.inflateMenu(R.menu.doctor_drawer);
                        welcomeFragment.setFabVisibility(false);
                    }
                    displayFragment(welcomeFragment);
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

        viewModel.getActiveAppointments().observe(this, new Observer<List<AppointmentDB>>() {
            @Override
            public void onChanged(List<AppointmentDB> appointmentDBS) {
                activeAppointments = appointmentDBS;
            }
        });

        viewModel.getPastAppointments().observe(this, new Observer<List<AppointmentDB>>() {
            @Override
            public void onChanged(List<AppointmentDB> appointmentDBS) {
                pastAppointments = appointmentDBS;
                if(appointmentsFragment != null)
                    appointmentsFragment.refresh(pastAppointments);
            }
        });

        viewModel.getAppointmentToView().observe(this, new Observer<PatientAppointment>() {
            @Override
            public void onChanged(PatientAppointment appointment) {
                Intent intent = new Intent(getApplicationContext(), AppointmentDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("appointment", appointment);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //handling the navigation
        navigationView.setNavigationItemSelectedListener(this);

    }

    //puts the options in the toolbar (only log out)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_button, menu);
        return true;
    }

    //listener for the toolbar options
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

    //listener for the navigations
    //to be filled with actions later
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: {
                displayFragment(WelcomeFragment.newInstance(username));
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_active_appointments: {
                appointmentsFragment = AppointmentsFragment.newInstance();
                appointmentsFragment.setData(activeAppointments);
                appointmentsFragment.setAppointmentsType(AppointmentListType.UPCOMING);
                changeFragment(appointmentsFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_pastAppointments: {
                appointmentsFragment = AppointmentsFragment.newInstance();
                viewModel.fetchPastAppointments();
                appointmentsFragment.setData(pastAppointments);
                appointmentsFragment.setAppointmentsType(AppointmentListType.PAST);
                changeFragment(appointmentsFragment);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            case R.id.nav_appointments: {
                Intent intent = new Intent(getApplicationContext(), DoctorViews.class);
                startActivity(intent);
                return true;
            }
            default:
                return false;
        }
    }

    //this page works based on fragments
    public void displayFragment(Fragment f) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.fragmentContainer,
                f);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void changeFragment(Fragment f) {

//        if (fragment==null) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, f);
        transaction.addToBackStack(null);
        transaction.commit();
//        }
//        else
//        {
//
//        }
    }

    //search doctors
    public void fab(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchDoctors.class);
        startActivity(intent);
    }

    public void getAppointmentDetails(AppointmentDB appointmentDB) {
        viewModel.getAppointentDetails(appointmentDB.getId());
    }

    @Override
    public List<AppointmentDB> getUpcomingAppointments() {
        return activeAppointments;
    }

    public List<AppointmentDB> getPastAppointments() {
        return pastAppointments;
    }

    @Override
    public List<AppointmentDB> getDaysAppointments() {
        return null;
    }
}
