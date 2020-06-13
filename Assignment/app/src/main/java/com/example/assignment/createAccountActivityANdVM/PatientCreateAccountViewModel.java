package com.example.assignment.createAccountActivityANdVM;

import android.app.Application;
import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.Patient;
import com.example.assignment.R;
import com.example.assignment.Shared.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

public class PatientCreateAccountViewModel extends AndroidViewModel {
    private Date date;
    private MutableLiveData<String> birthday;
    private MutableLiveData<String> errorMessage;

    public PatientCreateAccountViewModel(@NonNull Application application) {
        super(application);
        birthday = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    //make the string to show on the screen
    public void setDate(int day, int month, int year) {
        date = new Date(year-1900, month, day);
        birthday.postValue(DateTimeFormat.formatDate(date));
    }

    public MutableLiveData<String> getBirthday() {
        return birthday;
    }

    //validate data
    public boolean nextStep() {
        Calendar c = Calendar.getInstance();
        Date d2 = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        if(date == null) {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.emptyFields));
            return false;
        }
        else if(date.after(d2)) {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.invalidInput));
            return false;
        }
        else {
            errorMessage.postValue("");
            //update data in DataContainer
            DataContainer dc = DataContainer.getInstance();
            ((Patient)dc.getPerson()).setBirthday(date);
            return true;
        }
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
