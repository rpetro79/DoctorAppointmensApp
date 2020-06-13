package com.example.assignment.apiConnection;

import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.apiConnection.ApiModel.ApiDoctorAppointment;
import com.example.assignment.apiConnection.ApiModel.ApiPatientAppointment;

import java.util.ArrayList;
import java.util.List;

public class ListConverters {
    public static List<PatientAppointment> toPatientAppointmentList(List<ApiPatientAppointment> toConvert)
    {
        List<PatientAppointment> toReturn = new ArrayList<>();
        for(int i = 0; i < toConvert.size(); ++i)
        {
            toReturn.add(toConvert.get(i).getPatientAppointment());
        }
        return toReturn;
    }

    public static List<DoctorAppointment> toDoctorAppointmentList(List<ApiDoctorAppointment> toConvert)
    {
        List<DoctorAppointment> toReturn = new ArrayList<>();
        for(int i = 0; i < toConvert.size(); ++i)
        {
            toReturn.add(toConvert.get(i).getDoctorAppointment());
        }
        return toReturn;
    }
}
