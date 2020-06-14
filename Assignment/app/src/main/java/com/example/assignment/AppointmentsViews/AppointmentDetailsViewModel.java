package com.example.assignment.AppointmentsViews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.Repository;
import com.example.assignment.localDb.User;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class AppointmentDetailsViewModel extends AndroidViewModel {
    private MutableLiveData<PatientAppointment> appointment;
    private Repository repository;

    public AppointmentDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        appointment = new MutableLiveData<>();
        repository.addListener("putPatientAppointmentOkay", this :: putPatientAppointmentOkay);
    }

    private void putPatientAppointmentOkay(PropertyChangeEvent propertyChangeEvent) {
        PatientAppointment patientAppointment = appointment.getValue();
        Appointment app = (Appointment)propertyChangeEvent.getNewValue();
        patientAppointment.copyChanges(app);
        appointment.postValue(patientAppointment);
        //repository.updateAppointment(patientAppointment);
    }

    public LiveData<PatientAppointment> getAppointment() {
        return appointment;
    }

    public void setAppointment(PatientAppointment appointment) {
        this.appointment.postValue(appointment);
    }

    public void cancelAppointment()
    {
        PatientAppointment patientAppointment = appointment.getValue();
        patientAppointment.setCancelled(true);
        repository.putAppointment(patientAppointment.toAppointment());
    }

    public void logOut() {
        repository.logOut();
    }

    public LiveData<List<User>> getUser()
    {
        return repository.getUsers();
    }
}
