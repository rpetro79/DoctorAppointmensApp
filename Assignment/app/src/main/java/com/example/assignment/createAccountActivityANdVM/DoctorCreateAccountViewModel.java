package com.example.assignment.createAccountActivityANdVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.assignment.Model.Doctor;
import com.example.assignment.R;
import com.example.assignment.Repository;
import com.example.assignment.Shared.Checker;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class DoctorCreateAccountViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<List<String>> specializations;

    public DoctorCreateAccountViewModel(@NonNull Application application) {
        super(application);
        errorMessage = new MutableLiveData<>();
        repository = Repository.getInstance();
        repository.addListener("gotSpecializations", this::gotSpecializations);
        specializations = new MutableLiveData<>(new ArrayList<String>());
    }

    private void gotSpecializations(PropertyChangeEvent propertyChangeEvent) {
        specializations.postValue((List<String>)propertyChangeEvent.getNewValue());
    }

    public MutableLiveData<List<String>> getSpecializations() {
        return specializations;
    }

    //to populate the spinner
    public void fetchSpecializations()
    {
        repository.getSpecializations();
    }

    //validate data
    public boolean nextStep(String clinicName, String cvr, String address, String specialization)
    {
        if(clinicName.length() == 0 || cvr.length() == 0 || address.length() == 0 || specialization.length() == 0)
        {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.emptyFields));
            return false;
        }
        else if(!Checker.checkNumber(cvr))
        {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.invalidInput));
            return false;
        }
        else {
            //update data in DataContainer
            DataContainer dc = DataContainer.getInstance();
            ((Doctor)dc.getPerson()).setClinicName(clinicName);
            ((Doctor)dc.getPerson()).setAddress(address);
            ((Doctor)dc.getPerson()).setCvr(cvr);
            ((Doctor)dc.getPerson()).setSpecialization(specialization);
            errorMessage.postValue("");
            return true;
        }
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
