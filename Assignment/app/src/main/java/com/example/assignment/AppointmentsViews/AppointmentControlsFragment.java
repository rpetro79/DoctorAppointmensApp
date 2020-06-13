package com.example.assignment.AppointmentsViews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.assignment.R;

public class AppointmentControlsFragment extends Fragment {
    private Button cancel;

    public AppointmentControlsFragment() {
        // Required empty public constructor
    }

    public static AppointmentControlsFragment newInstance() {
        AppointmentControlsFragment fragment = new AppointmentControlsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_controls, container, false);
        cancel = (Button)view.findViewById(R.id.cancelAppointmentButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAppointment();
            }
        });
        return view;
    }

    private void cancelAppointment() {
        AppointmentCancellationConfirmationDialog confirmation = new AppointmentCancellationConfirmationDialog();
        confirmation.show(getParentFragmentManager(), "confirm");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
