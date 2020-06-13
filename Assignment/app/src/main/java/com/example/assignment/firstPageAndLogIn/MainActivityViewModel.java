package com.example.assignment.firstPageAndLogIn;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.util.StringUtil;

import com.example.assignment.Model.UserType;
import com.example.assignment.R;
import com.example.assignment.Repository;
import com.example.assignment.Shared.Checker;
import com.example.assignment.localDb.User;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> message;
    private UserType userType;
    private Repository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        message = new MutableLiveData<>();
        repository = Repository.getInstance(application);
        repository.addListener("authenticationError", this :: authenticationError);
    }

    private void authenticationError(PropertyChangeEvent propertyChangeEvent) {
        message.postValue(getApplication().getResources().getString(R.string.wrongCredentials));
    }

    //check log in
    public void logIn(String email, String password) {
        if(email.isEmpty() || password.isEmpty())
        {
            message.postValue(getApplication().getApplicationContext().getResources().getString(R.string.emptyFields));
        }
        else if(!Checker.checkEmail(email))
        {
            message.postValue(getApplication().getApplicationContext().getResources().getString(R.string.invalidInput));
        }
        else
        {
            repository.logIn(email, password, userType);
            message.postValue("");
        }
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    //to check if the user is logged in
    public LiveData<List<User>> userLoggedIn() {
         return repository.getUsers();
    }

    public void setUser(User user) {
        repository.setUser(user);
    }
}
