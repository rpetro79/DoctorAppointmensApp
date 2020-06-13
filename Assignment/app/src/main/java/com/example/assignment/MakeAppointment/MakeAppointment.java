package com.example.assignment.MakeAppointment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.assignment.R;
import com.example.assignment.Shared.DateTimeFormat;
import com.example.assignment.Shared.EditAppointmentInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MakeAppointment extends Fragment {

    private EditText label;
    private EditText symptoms;
    private String labelDefault;
    private long appointmentDate;
    private long appointmentTime;
    private List<Long> dat;
    private List<String> datStrings;
    private TextView displayAppointmentDate;
    private TextView errorMessage;
    private Spinner timesSpinner;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button done;

    public MakeAppointment(String specialization) {
        this.labelDefault = specialization + " appointment";
    }

    public static MakeAppointment newInstance(String specialization) {
        MakeAppointment fragment = new MakeAppointment(specialization);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_appointment, container, false);

        label = view.findViewById(R.id.label);
        label.setText(labelDefault);
        symptoms = view.findViewById(R.id.symptoms);
        displayAppointmentDate = view.findViewById(R.id.appointmentDate);
        errorMessage = view.findViewById(R.id.errorMessageMakeAppointment);

        done = (Button)view.findViewById(R.id.doneButtonMakeAppointment);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAppointment();
            }
        });

        timesSpinner = view.findViewById(R.id.chooseTimeOfAppointment);
        timesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appointmentTime = dat.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                appointmentTime = 0;
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date date = new Date(year-1900, month, dayOfMonth);
                appointmentDate = date.getTime();
                displayAppointmentDate.setText(DateTimeFormat.formatDate(date));
                ((EditAppointmentInterface)getActivity()).getDATForDay(appointmentDate);
            }
        };
        return view;
    }

    private void sendAppointment() {
        ((EditAppointmentInterface)getActivity()).sendAppointment(getDateTime(), getSymptoms(), getLabel());
    }

    public void setDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                dateSetListener,
                year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() + 1800000);
        dialog.show();
    }

    public void gotDATForDay(List<Long> dat)
    {
        this.dat = dat;
        datStrings = new ArrayList<String>();
        for(int i = 0; i < dat.size(); ++i)
        {
            String hour, minutes;
            int h = (int)(dat.get(i)/1000/60/60);
            int min = (int)(dat.get(i)/1000/60 - h*60);
            if(h < 10)
                hour = "0" + h;
            else hour = "" + h;
            if(min < 10)
                minutes = "0" + min;
            else minutes = "" + min;
            datStrings.add("" + hour + ":" + minutes);
        }
        initializeSpinner();
    }

    private void initializeSpinner() {
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, datStrings
        );
        timeAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        timesSpinner.setAdapter(timeAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public long getDateTime() {
        if(appointmentDate == 0)
            return 0;
        return (appointmentDate + appointmentTime);
    }

    public String getSymptoms() {
        return symptoms.getText().toString();
    }

    public String getLabel() {
        return label.getText().toString();
    }

    public void setErrorMessage(String message)
    {
        errorMessage.setText(message);
    }

    public void error(String s) {
        errorMessage.setText(s);
    }
}
