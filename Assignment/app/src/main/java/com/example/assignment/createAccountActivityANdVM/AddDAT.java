package com.example.assignment.createAccountActivityANdVM;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.R;

public class AddDAT extends AppCompatActivity {
    private AddDATViewModel viewModel;
    private EditText hours;
    private EditText minutes;
    private TextView target;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dat);

        viewModel = new ViewModelProvider(this).get(AddDATViewModel.class);

        hours = findViewById(R.id.hours);
        minutes = findViewById(R.id.minutes);
        target = findViewById(R.id.displayTimes);
    }

    public void addDAT(View view) {
        int hour, minute;
        String hoursString = hours.getText().toString();
        String minuteString = minutes.getText().toString();

        if(viewModel.addTime(hoursString, minuteString))
        {
            s += (hoursString + ":" + minuteString + "\n");
            target.setText(s);
        }
        else
            Toast.makeText(getApplicationContext(), "Incorrect input", Toast.LENGTH_SHORT).show();


    }
    public void undoDAT(View view)
    {
        viewModel.undo();
        int i = s.indexOf('\n', s.length()-8);
        if(i != s.length()-1)
        s = s.substring(0, i+1);
        else s = "";
        target.setText(s);
    }


    public void next(View view) {
        viewModel.next();
        Intent intent = new Intent(getApplicationContext(), CreateAccountStep3.class);
        startActivityForResult(intent, 4);
    }

    //wait until the account is created to finish the activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4)
        {
            if(resultCode == RESULT_OK)
            {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            else if(resultCode == RESULT_CANCELED)
            {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
    }
}
