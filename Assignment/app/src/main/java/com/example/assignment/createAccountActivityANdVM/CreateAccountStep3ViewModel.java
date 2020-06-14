package com.example.assignment.createAccountActivityANdVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.R;
import com.example.assignment.Repository;
import com.example.assignment.localDb.User;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class CreateAccountStep3ViewModel extends AndroidViewModel {
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Boolean> connectionError;
    private Repository repository;

    public CreateAccountStep3ViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance();
        errorMessage = new MutableLiveData<>();
        repository.addListener("errorPostingUser", this::errorPostingUser);
        repository.addListener("authenticationError", this::errorPostingUser);
        connectionError = new MutableLiveData<>(false);
    }

    private void errorPostingUser(PropertyChangeEvent propertyChangeEvent) {
        connectionError.postValue(true);
    }

    public LiveData<Boolean> getConnectionError() {
        return connectionError;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    //validate data
    public boolean createAccount(String password1, String password2)
    {
        if(password1.length() == 0 || password2.length() == 0)
        {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.emptyFields));
            return false;
        }
        else if(!password1.equals(password2))
        {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.passwordsDontMatch));
            return false;
        }
        else if(password1.length() < 6)
        {
            errorMessage.postValue(getApplication().getApplicationContext().getResources().getString(R.string.passwordLongerThan6));
            return false;
        }
        else{
            errorMessage.postValue("");
            //use the data in DataContainer to create the account
            DataContainer container = DataContainer.getInstance();
            repository.createAccount(container.getPerson().getEmail(), password1);

            return true;
        }
    }

    public LiveData<List<User>> getUsers()
    {
        return repository.getUsers();
    }
}
