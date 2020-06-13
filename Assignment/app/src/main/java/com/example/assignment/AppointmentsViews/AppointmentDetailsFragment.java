package com.example.assignment.AppointmentsViews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.R;
import com.example.assignment.Shared.DateTimeFormat;

public class AppointmentDetailsFragment extends Fragment {

    private TextView label;
    private TextView datetime;
    private TextView doctor;
    private TextView clinic;
    private TextView address;
    private TextView symptoms;
    private TextView cancelled;

    private PatientAppointment appointment;

    private AppointmentDetailsFragment(PatientAppointment appointment) {
        this.appointment = appointment;
    }

    public static AppointmentDetailsFragment newInstance(PatientAppointment appointment) {
        AppointmentDetailsFragment fragment = new AppointmentDetailsFragment(appointment);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_details, container, false);

        label = view.findViewById(R.id.labelAppointmentDetails);
        datetime = view.findViewById(R.id.datetimeAppointmentDetails);
        doctor = view.findViewById(R.id.doctorNameAppointmentDetails);
        clinic = view.findViewById(R.id.clinicAppointmentDetails);
        address = view.findViewById(R.id.addressAppointmentDetails);
        symptoms = view.findViewById(R.id.symptomsAppointmentDetails);
        cancelled = view.findViewById(R.id.cancelledAppointmentDetails);
        if(appointment != null)
        {
            loadText();
        }

        return view;
    }

    public void loadText()
    {
        if(label != null)
        {
            label.setText(appointment.getCustomerLabel());
            datetime.setText(DateTimeFormat.formatDateTime(appointment.getDatetime()));
            String helper = String.format(getResources().getString(R.string.doctorText), appointment.getDoctor().getName());
            doctor.setText(helper);
            helper = String.format(getResources().getString(R.string.clinicText), appointment.getDoctor().getClinicName());
            clinic.setText(helper);
            helper = String.format(getResources().getString(R.string.addressText),
                    appointment.getDoctor().getAddress(), appointment.getDoctor().getCity(), appointment.getDoctor().getCountry());
            address.setText(helper);
            symptoms.setText(String.format(getResources().getString(R.string.symptomsText), appointment.getSymptoms()));

            if(appointment.isCancelled())
            {
                cancelled.setText(getActivity().getApplicationContext().getResources().getString(R.string.cancelled));
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setAppointment(PatientAppointment patientAppointment) {
        this.appointment = patientAppointment;
        loadText();
    }
}
