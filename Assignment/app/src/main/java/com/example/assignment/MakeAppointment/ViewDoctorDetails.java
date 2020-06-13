package com.example.assignment.MakeAppointment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment.Model.Doctor;
import com.example.assignment.R;

public class ViewDoctorDetails extends Fragment {
    private Doctor doctor;
    private TextView name;
    private TextView clinicName;
    private TextView address;
    private TextView phone;
    private TextView email;

    public ViewDoctorDetails(Doctor doctor) {
        this.doctor = doctor;
    }

    public static ViewDoctorDetails newInstance(Doctor doctor) {
        ViewDoctorDetails fragment = new ViewDoctorDetails(doctor);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_doctor_details, container, false);

        name = view.findViewById(R.id.doctorNameDoctorView);
        clinicName = view.findViewById(R.id.clinicNameDoctorView);
        address = view.findViewById(R.id.addressDoctorView);
        email = view.findViewById(R.id.emailDoctorView);
        phone = view.findViewById(R.id.phoneDoctorView);

        name.setText(doctor.getName());
        String clinic = String.format(getResources().getString(R.string.clinicText), doctor.getClinicName());
        clinicName.setText(clinic);
        String addr = String.format(getResources().getString(R.string.addressText),
                doctor.getAddress(), doctor.getCity(), doctor.getCountry());
        address.setText(addr);
        String emailAddress = String.format(getResources().getString(R.string.emailText), doctor.getEmail());
        String phoneNumber = String.format(getResources().getString(R.string.phoneText), doctor.getPhone());
        email.setText(emailAddress);
        phone.setText(phoneNumber);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
