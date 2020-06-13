package com.example.assignment.MakeAppointment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.Doctor;
import com.example.assignment.Model.Patient;
import com.example.assignment.R;
import com.example.assignment.Repository;
import com.example.assignment.localDb.User;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> countries;
    private MutableLiveData<List<String>> regions;
    private MutableLiveData<List<String>> cities;
    private MutableLiveData<List<String>> specializations;
    private Repository repository;
    private String specialization;
    private MutableLiveData<List<Doctor>> doctors;
    private MutableLiveData<List<Long>> dats;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<NewAppointmentState> appointmentState;
    private MutableLiveData<Boolean> abort;
    private MutableLiveData<String> connectionError;


    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();

        repository.addListener("gotCountries", this::gotCountries);
        repository.addListener("gotRegions", this::gotRegions);
        repository.addListener("gotCities", this::gotCities);
        repository.addListener("gotSpecializations", this::gotSpecializations);
        repository.addListener("gotPatientLocation", this::gotPatientLocation);
        repository.addListener("gotSearchDoctors", this :: gotSearchDoctors);
        repository.addListener("gotDATForDate", this :: gotDATForDate);
        repository.addListener("postAppointmentOkay", this :: postAppointmentOkay);
        repository.addListener("postAppointmentFail", this :: postAppointmentFail);
        repository.addListener("gotSpecializationsFail", this :: gotSpecializationsFail);
        repository.addListener("gotSearchDoctorsFail", this :: gotSearchDoctorsFail);

        countries = new MutableLiveData<List<String>>(new ArrayList<String>());
        regions = new MutableLiveData<List<String>>(new ArrayList<String>());
        cities = new MutableLiveData<List<String>>(new ArrayList<String>());
        specializations = new MutableLiveData<List<String>>(new ArrayList<String>());
        doctors = new MutableLiveData<List<Doctor>>();
        dats = new MutableLiveData<List<Long>>();
        errorMessage = new MutableLiveData<String>();
        appointmentState = new MutableLiveData<NewAppointmentState>(NewAppointmentState.NOT_POSTED);
        abort = new MutableLiveData<Boolean>(false);
        connectionError = new MutableLiveData<String>("");
    }

    private void gotSearchDoctorsFail(PropertyChangeEvent propertyChangeEvent) {
        connectionError.postValue(getApplication().getApplicationContext().getResources().getString(R.string.cannotFetchDoctors));
    }

    private void gotSpecializationsFail(PropertyChangeEvent propertyChangeEvent) {
        abort.postValue(true);
    }

    private void postAppointmentFail(PropertyChangeEvent propertyChangeEvent) {
        appointmentState.postValue(NewAppointmentState.POST_ERROR);
    }

    private void postAppointmentOkay(PropertyChangeEvent propertyChangeEvent) {
        appointmentState.postValue(NewAppointmentState.POSTED);
    }

    private void gotDATForDate(PropertyChangeEvent propertyChangeEvent) {
        dats.postValue(((List<Long>)propertyChangeEvent.getNewValue()));
    }

    private void gotSearchDoctors(PropertyChangeEvent propertyChangeEvent) {
        doctors.postValue(((List<Doctor>)propertyChangeEvent.getNewValue()));
    }

    public MutableLiveData<String> getConnectionError() {
        return connectionError;
    }

    public MutableLiveData<NewAppointmentState> getAppointmentState() {
        return appointmentState;
    }

    public LiveData<List<Doctor>> getDoctors() {
        return doctors;
    }

    private void gotPatientLocation(PropertyChangeEvent propertyChangeEvent) {
        Patient p = (Patient)propertyChangeEvent.getNewValue();
        repository.getDoctors(specialization, p.getCountry(), p.getCity());
    }

    private void gotSpecializations(PropertyChangeEvent propertyChangeEvent) {
        specializations.postValue((List<String>)propertyChangeEvent.getNewValue());
    }

    private void gotCountries(PropertyChangeEvent propertyChangeEvent) {
        countries.postValue((List<String>)propertyChangeEvent.getNewValue());
    }

    private void gotRegions(PropertyChangeEvent propertyChangeEvent) {
        regions.postValue((List<String>)propertyChangeEvent.getNewValue());
    }

    private void gotCities(PropertyChangeEvent propertyChangeEvent) {
        cities.postValue((List<String>)propertyChangeEvent.getNewValue());
    }

    public LiveData<List<Long>> getDats() {
        return dats;
    }

    public LiveData<List<String>> getSpecializations() {
        return specializations;
    }

    public LiveData<List<String>> getCountries() {
        return countries;
    }

    public LiveData<List<String>> getRegions() {
        return regions;
    }

    public LiveData<List<String>> getCities() {
        return cities;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchCountries() {
        repository.getAllCountries();
    }

    public void fetchRegions(String selectedCountry) {
        repository.getRegions(selectedCountry);
    }

    public void fetchCities(String selectedCountry, String selectedRegion) {
        repository.getCities(selectedCountry, selectedRegion);
    }

    public void fetchSecializations() {
        repository.getSpecializations();
    }

    public void getDoctors(String specialization, String country, String city) {
        repository.getDoctors(specialization, country, city);
    }

    public void logOut() {
        repository.logOut();
    }

    public LiveData<List<User>> getUser()
    {
        return repository.getUsers();
    }

    public void getDATForDay(String doctorId, long time) {
        repository.getDATForDay(doctorId, time);
    }

    public LiveData<Boolean> Abort() {
        return abort;
    }

    public void postAppointment(String userId, String personId, long dateTime, String symptoms, String label) {
        if(symptoms == null || symptoms.length() == 0 || label == null || label.length() == 0 || dateTime == 0)
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.invalidInput));
        else repository.postAppointment(new Appointment(userId, personId, dateTime, symptoms, label, false));
    }
}
