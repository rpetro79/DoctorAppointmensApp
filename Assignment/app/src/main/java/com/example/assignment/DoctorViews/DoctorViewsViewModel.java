package com.example.assignment.DoctorViews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Repository;
import com.example.assignment.Shared.DateTimeFormat;
import com.example.assignment.localDb.AppointmentDB;
import com.example.assignment.localDb.User;

import java.beans.PropertyChangeEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DoctorViewsViewModel extends AndroidViewModel {
    private Date date;
    private MutableLiveData<Date> liveDataDate;
    private MutableLiveData<String> dateString;
    private MutableLiveData<DoctorAppointment> appointment;
    private Repository repository;
    private MutableLiveData<Boolean> cannotFetchAppointment;

    public DoctorViewsViewModel(@NonNull Application application) {
        super(application);

        repository = Repository.getInstance();

        Calendar cal = Calendar.getInstance();
        liveDataDate = new MutableLiveData<Date>();
        dateString = new MutableLiveData<>("");
        dateString = new MutableLiveData<>();
        appointment = new MutableLiveData<>();
        cannotFetchAppointment = new MutableLiveData<>();
        repository.addListener("gotDoctorAppointment", this::gotDoctorAppointment);
        repository.addListener("gotDoctorAppointmentFail", this::gotDoctorAppointmentFail);
    }

    private void gotDoctorAppointmentFail(PropertyChangeEvent propertyChangeEvent) {
        cannotFetchAppointment.postValue(true);
    }

    private void gotDoctorAppointment(PropertyChangeEvent propertyChangeEvent) {
        DoctorAppointment ap = (DoctorAppointment) propertyChangeEvent.getNewValue();
        appointment.postValue(ap);
    }

    public MutableLiveData<DoctorAppointment> getAppointment() {
        return appointment;
    }

    public LiveData<List<AppointmentDB>> getAppointments()
    {
        return repository.getActiveAppointmentsDB();
    }

    public void setDate(int year, int month, int dayOfMonth) {
        this.date = new Date(year - 1900, month, dayOfMonth);
        dateString.postValue(DateTimeFormat.formatDate(date));
        liveDataDate.postValue(date);
        repository.getDoctorAppointmentsForDay(date);
    }

    public LiveData<String> getDateString() {
        return dateString;
    }

    public void setDate(Date date) {
        this.date = date;
        dateString.postValue(DateTimeFormat.formatDate(date));
        liveDataDate.postValue(date);
    }

    public void getAppointentDetails(int id) {
        repository.getDoctorAppointment(id);
    }

    public void logOut() {
        repository.logOut();
    }

    public LiveData<List<User>> getUser()
    {
        return repository.getUsers();
    }

    public LiveData<Boolean> getCannotFetchAppointment() {
        return cannotFetchAppointment;
    }
}
