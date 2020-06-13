package com.example.assignment.AppointmentsViews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Repository;
import com.example.assignment.localDb.User;

import java.util.List;

public class DoctorAppointmentDetailsViewModel extends AndroidViewModel {
    private MutableLiveData<DoctorAppointment> appointment;
    private Repository repository;

    public DoctorAppointmentDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        appointment = new MutableLiveData<>();
    }

    public void setAppointment(DoctorAppointment appointment) {
        this.appointment.postValue(appointment);
    }

    public void logOut()
    {
        repository.logOut();
    }

    public LiveData<DoctorAppointment> getAppointment() {
        return appointment;
    }

    public LiveData<List<User>> getUser()
    {
        return repository.getUsers();
    }
}
