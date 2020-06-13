package com.example.assignment.apiConnection.ApiModel;

import com.example.assignment.Model.DoctorAppointment;

public class ApiDoctorAppointment {
    private int appointmentId;
    private String doctorId;
    private ApiPatient patient;
    private long datetime;
    private String symptoms;
    private boolean cancelled;

    public DoctorAppointment getDoctorAppointment()
    {
        return new DoctorAppointment(appointmentId, patient.getPatient(), doctorId, datetime, symptoms, cancelled);
    }
}
