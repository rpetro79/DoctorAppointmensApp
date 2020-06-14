package com.example.assignment.createAccountActivityANdVM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.R;
import com.example.assignment.localDb.User;

import java.util.List;

public class CreateAccountStep3 extends AppCompatActivity {
    private TextView errorMessage;
    private EditText password1;
    private EditText password2;
    private ProgressBar progressBar;
    private CreateAccountStep3ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_step3);

        viewModel = new ViewModelProvider(this).get(CreateAccountStep3ViewModel.class);

        progressBar = findViewById(R.id.progressBarCreateAccount3);
        progressBar.setVisibility(View.INVISIBLE);
        errorMessage = findViewById(R.id.errorMessageCreateAccountStep3);
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                errorMessage.setText(s);
                if(s.length() > 0)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(users.size() != 0)
                {
                    logIn();
                }
            }
        });

        viewModel.getConnectionError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), R.string.authenticationError, Toast.LENGTH_LONG).show();
                }
            }
        });

        password1 = findViewById(R.id.passwordCreateAccount);
        password2 = findViewById(R.id.repeatPasswordCreateAccount);
    }

    //finish the activity and send results back, to finish the other activities on the stack
    //no need to make an intent to go to the next step, since it is already handled in the main activity
    //in repository, after calling the backend to create the account, the user data is saved to the database, just like after log in,
    //so the main activity is triggered and will open the next activity
    private void logIn() {
        Intent intent2 = new Intent();
        setResult(RESULT_OK, intent2);
        finish();
    }

    public void createAccount(View view) {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.createAccount(password1.getText().toString(), password2.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
    }
}
