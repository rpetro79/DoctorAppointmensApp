package com.example.assignment.AppointmentsViews;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment.R;
import com.example.assignment.WelcomePage.Welcome;
import com.example.assignment.localDb.AppointmentDB;

import java.util.List;

public class AppointmentsFragment extends Fragment implements AppointmentAdapter.OnListItemClickListener{
    private List<AppointmentDB> appointments;
    private AppointmentAdapter adapter;
    private RecyclerView appointmentList;
    private AppointmentListType appointmentsType;

    public AppointmentsFragment() {
        // Required empty public constructor
    }

    public static AppointmentsFragment newInstance() {
        AppointmentsFragment fragment = new AppointmentsFragment();
        return fragment;
    }

    public void setData(List<AppointmentDB> apps)
    {
        appointments = apps;
        adapter = new AppointmentAdapter(apps, this::onListItemClick);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        appointmentList = view.findViewById(R.id.appointmentsList);
        appointmentList.hasFixedSize();
        appointmentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(appointmentsType == AppointmentListType.UPCOMING)
            appointments = ((AppointmentFragmentActivityInterface)getActivity()).getUpcomingAppointments();
        else if(appointmentsType == AppointmentListType.PAST)
            appointments = ((AppointmentFragmentActivityInterface)getActivity()).getPastAppointments();
        else appointments = ((AppointmentFragmentActivityInterface)getActivity()).getDaysAppointments();
        adapter = new AppointmentAdapter(appointments, this);
        appointmentList.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        ((AppointmentFragmentActivityInterface) getActivity()).getAppointmentDetails(appointments.get(clickedItemIndex));
    }

    public AppointmentListType appointmentListType() {
        return appointmentsType;
    }

    public void setAppointmentsType(AppointmentListType appointmentsType) {
        this.appointmentsType = appointmentsType;
    }

    public void refresh(List<AppointmentDB> appointments) {
        this.appointments = appointments;
        adapter = new AppointmentAdapter(this.appointments, this);
        appointmentList.setAdapter(adapter);
    }
}
