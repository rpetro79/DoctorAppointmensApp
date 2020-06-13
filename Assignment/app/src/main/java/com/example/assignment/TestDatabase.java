package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assignment.localDb.User;

import java.util.List;

public class TestDatabase extends AppCompatActivity {
    TextView ssn;
    TextView name;
    TextView type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);

        Repository repository = Repository.getInstance(getApplication());

        ssn = findViewById(R.id.testSsn);
        name = findViewById(R.id.testName);
        type = findViewById(R.id.testType);
        repository.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                ssn.setText(users.get(0).getUserId());
                name.setText(users.get(0).getName());
                type.setText(users.get(0).getUserType().toString());
            }
        });
    }
}
