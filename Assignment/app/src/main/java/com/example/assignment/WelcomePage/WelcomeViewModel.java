package com.example.assignment.WelcomePage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.Model.UserType;
import com.example.assignment.Repository;
import com.example.assignment.localDb.AppointmentDB;
import com.example.assignment.localDb.User;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class WelcomeViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<PatientAppointment> appointmentToView;
    private MutableLiveData<Boolean> cannotFetchAppointment;

    public WelcomeViewModel(@NonNull Application application) {
        super(application);
        appointmentToView = new MutableLiveData<>();
        cannotFetchAppointment = new MutableLiveData<>(false);
        repository = Repository.getInstance();
        repository.addListener("gotPatientAppointment", this :: gotPatientAppointment);
        repository.addListener("gotPatientAppointmentFail", this :: gotPatientAppointmentFail);
    }

    public MutableLiveData<Boolean> getCannotFetchAppointment() {
        return cannotFetchAppointment;
    }

    private void gotPatientAppointmentFail(PropertyChangeEvent propertyChangeEvent) {
        cannotFetchAppointment.postValue(true);
    }

    private void gotPatientAppointment(PropertyChangeEvent propertyChangeEvent) {
        appointmentToView.postValue(((PatientAppointment) propertyChangeEvent.getNewValue()));
    }

    public void logOut() {
        repository.logOut();
    }

    public LiveData<List<User>> getUser() {
        return repository.getUsers();
    }

    public LiveData<List<AppointmentDB>> getActiveAppointments()
    {
        return repository.getActiveAppointmentsDB();
    }

    public LiveData<List<AppointmentDB>> getPastAppointments()
    {
        return repository.getPastAppointmentsDB();
    }

    public MutableLiveData<PatientAppointment> getAppointmentToView() {
        return appointmentToView;
    }

    public void fetchPastAppointments() {
        repository.fetchPastAppointments();
    }

    public void getAppointentDetails(int id) {
        repository.getPatientAppointment(id);
    }
}
