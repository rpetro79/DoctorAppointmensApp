package com.example.assignment.firstPageAndLogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment.WelcomePage.Welcome;
import com.example.assignment.createAccountActivityANdVM.CreateAccount;
import com.example.assignment.Model.UserType;
import com.example.assignment.R;
import com.example.assignment.localDb.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private boolean observerCreated = false;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayFragment(FirstPageFragment.newInstance());

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        //check if the user is logged in and change the activity if it is or when the user logs in
        viewModel.userLoggedIn().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(users != null && users.size() != 0)
                {
                    user = users.get(0);
                    checkLoggedIn();
                }
            }
        });

    }

    //for showing/replacing fragments
    public void displayFragment(Fragment f) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragmentContainer,
                f).commit();
    }

    public void changeFragment(Fragment f) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, f);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    //changes the fragment and sets the user type based on the user's selection
    public void openLogInActivity(View view) {
        if(!observerCreated)
        {
            viewModel.getMessage().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    TextView t = findViewById(R.id.errorMessage);
                    t.setText(s);
                }
            });
            observerCreated = true;
        }

        if(view.getId() == R.id.doctorLogIn)
            viewModel.setUserType(UserType.DOCTOR);
        else
            viewModel.setUserType(UserType.PATIENT);
        changeFragment(LogInFragment.newInstance());
    }

    //finishes activity when results are received -- check this
    public void openCreateAccountActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
        startActivity(intent);
    }

    //log in
    public void logIn(View view) {
        EditText email = findViewById(R.id.emailLogIn);
        EditText password = findViewById(R.id.password);
        viewModel.logIn(email.getText().toString(), password.getText().toString());
    }

    //when the user logs in/is already logged in
    private void checkLoggedIn()
    {
        if(user != null){
            viewModel.setUser(user);
            Intent intent = new Intent(getApplicationContext(), Welcome.class);
            startActivity(intent);
            finish();
        }
    }
}
