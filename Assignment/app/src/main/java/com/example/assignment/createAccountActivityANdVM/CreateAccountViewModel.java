package com.example.assignment.createAccountActivityANdVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.Doctor;
import com.example.assignment.Model.Patient;
import com.example.assignment.Model.Person;
import com.example.assignment.Model.UserType;
import com.example.assignment.R;
import com.example.assignment.Repository;
import com.example.assignment.Shared.Checker;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class CreateAccountViewModel extends AndroidViewModel{
    private Repository repository;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<List<String>> countries;
    private MutableLiveData<List<String>> regions;
    private MutableLiveData<List<String>> cities;

    public CreateAccountViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        errorMessage = new MutableLiveData<>();

        countries = new MutableLiveData<List<String>>(new ArrayList<String>());
        regions = new MutableLiveData<List<String>>(new ArrayList<String>());
        cities = new MutableLiveData<List<String>>(new ArrayList<String>());

        repository.addListener("gotCountries", this::gotCountries);
        repository.addListener("gotRegions", this::gotRegions);
        repository.addListener("gotCities", this::gotCities);
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

    public LiveData<List<String>> getCountries() {
        return countries;
    }

    public LiveData<List<String>> getRegions() {
        return regions;
    }

    public LiveData<List<String>> getCities() {
        return cities;
    }

    public void fetchCountries() {
        repository.getAllCountries();
    }

    public void fetchRegions(String country) {
        repository.getRegions(country);
    }

    public void fetchCities(String country, String region) {
        repository.getCities(country, region);
    }

    //validate data
    public boolean secondStep(String ssn, String name, String phone, String email, String country, String city, UserType userType) {
        if(ssn.length() == 0 || name.length() == 0 || phone.length() == 0 || email.length() == 0)
        {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.emptyFields));
            return false;
        }
        if(!(Checker.checkNumber(ssn) && Checker.checkEmail(email) && Checker.checkNumber(phone)))
        {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.invalidInput));
            return false;
        }
        else
        {
            errorMessage.postValue("");
            //store data in DataContainer for the next steps
            DataContainer container = DataContainer.getInstance();
            Person person;
            if(userType == UserType.DOCTOR)
                person = new Doctor(ssn, name, email, phone, country, city);
            else
                person = new Patient(ssn, name, email, phone, country, city);
            container.setPerson(person, userType);
            return true;
        }
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
