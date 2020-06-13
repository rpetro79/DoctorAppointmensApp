package com.example.assignment.MakeAppointment;

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
import android.view.View;
import android.widget.Toast;

import com.example.assignment.MakeAppointment.doctorList.DoctorList;
import com.example.assignment.MakeAppointment.search.SearchDoctorsFragment;
import com.example.assignment.Model.Doctor;
import com.example.assignment.R;
import com.example.assignment.Shared.EditAppointmentInterface;
import com.example.assignment.WelcomePage.Welcome;
import com.example.assignment.localDb.User;

import java.util.List;

public class SearchDoctors extends AppCompatActivity implements EditAppointmentInterface {
    private SearchViewModel viewModel;
    private SearchDoctorsFragment sdf;
    private MakeAppointment maf;
    private DoctorList dl;
    private Doctor selectedDoctor;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctors);

        Toolbar toolbar = findViewById(R.id.myToolbarSimpleView);
        setSupportActionBar(toolbar);

        sdf = SearchDoctorsFragment.newInstance();

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        viewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(users != null && users.size() != 0)
                {
                    user = users.get(0);
                    sdf.setCountry(users.get(0).getCountry());
                    sdf.setCity(users.get(0).getCity());
                }
                else {
                    finish();
                }
            }
        });

        viewModel.getCountries().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                sdf.gotCountries(strings);
            }
        });

        viewModel.getRegions().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                sdf.gotRegions(strings);

            }
        });

        viewModel.getCities().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                sdf.gotCities(strings);

            }
        });

        viewModel.getSpecializations().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                sdf.initializeSpinner(strings);
            }
        });

        viewModel.Abort().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true)
                    finish();
            }
        });

        viewModel.getDoctors().observe(this, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                if(dl != null)
                    dl.setDoctors(doctors);
            }
        });

        viewModel.getDats().observe(this, new Observer<List<Long>>() {
            @Override
            public void onChanged(List<Long> longs) {
                if(maf != null)
                    maf.gotDATForDay(longs);
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(maf != null)
                    maf.error(s);
            }
        });

        viewModel.getAppointmentState().observe(this, new Observer<NewAppointmentState>() {
            @Override
            public void onChanged(NewAppointmentState newAppointmentState) {
                if(newAppointmentState == NewAppointmentState.POSTED)
                {
                    Intent intent = new Intent(getApplicationContext(), Welcome.class);
                    startActivity(intent);
                    finish();
                }
                else if(newAppointmentState == NewAppointmentState.POST_ERROR)
                {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.appointmentPostFail), Toast.LENGTH_LONG).show();;
                }
            }
        });

        viewModel.getConnectionError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.length() > 0)
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        displayFragment(sdf);
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

    public void fetchSpecializations()
    {
        viewModel.fetchSecializations();
    }

    public void changeFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.simpleViewFragment, f);
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

        fragmentTransaction.add(R.id.simpleViewFragment,
                f);
        fragmentTransaction.commit();
    }

    public void fetchCountries() {
        viewModel.fetchCountries();
    }

    public void fetchCities(String selectedCountry, String selectedRegion) {
        viewModel.fetchCities(selectedCountry, selectedRegion);
    }

    public void fetchRegions(String selectedCountry) {
        viewModel.fetchRegions(selectedCountry);
    }

    public void searchDoctors(View view) {
        int x = sdf.getCheckedRadioButtonId();
        switch (x)
        {
            case R.id.localArea:
            {
                viewModel.getDoctors(sdf.getSpecialization(), sdf.getCountry(), sdf.getCity());
                break;
            }
            case R.id.selectArea:
            {
                String country = sdf.getSelectedCountry();
                String city = sdf.getSelectedCity();
                viewModel.getDoctors(sdf.getSpecialization(), country, city);
                break;
            }
        }
        dl = DoctorList.newInstance();
        changeFragment(dl);
    }

    public void viewDoctor(Doctor doctor) {
        selectedDoctor = doctor;
        changeFragment(ViewDoctorDetails.newInstance(doctor));
    }

    public void makeAppointment(View view) {
        maf = MakeAppointment.newInstance(selectedDoctor.getSpecialization());
        changeFragment(maf);
    }

    public void getDATForDay(long time) {
        viewModel.getDATForDay(selectedDoctor.getPersonId(), time);
    }

    public void sendAppointment(long datetime, String symptoms, String label) {
        viewModel.postAppointment(user.getUserId(), selectedDoctor.getPersonId(), datetime, symptoms, label);
    }

    public void setDate(View view) {
        maf.setDate();
    }
}
