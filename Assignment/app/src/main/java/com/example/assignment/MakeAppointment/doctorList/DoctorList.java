package com.example.assignment.MakeAppointment.doctorList;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment.MakeAppointment.SearchDoctors;
import com.example.assignment.Model.Doctor;
import com.example.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorList extends Fragment implements DoctorAdapter.OnListItemClickListener {
    private RecyclerView doctorList;
    private List<Doctor> doctors = new ArrayList<>();
    private DoctorAdapter adapter;

    public DoctorList() {
        // Required empty public constructor
    }

    public static DoctorList newInstance() {
        DoctorList fragment = new DoctorList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        doctorList = view.findViewById(R.id.doctorList);
        doctorList.hasFixedSize();
        doctorList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DoctorAdapter(doctors, this);
        doctorList.setAdapter(adapter);
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

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
        adapter = new DoctorAdapter(doctors, this);
        doctorList.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        ((SearchDoctors)getActivity()).viewDoctor(doctors.get(clickedItemIndex));
    }
}
