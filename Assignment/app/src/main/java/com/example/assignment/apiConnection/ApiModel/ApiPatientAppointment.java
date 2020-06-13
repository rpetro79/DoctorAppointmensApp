package com.example.assignment.apiConnection.ApiModel;

import com.example.assignment.Model.Doctor;
import com.example.assignment.Model.PatientAppointment;
import com.google.gson.annotations.SerializedName;

public class ApiPatientAppointment {
    private int appointmentId;
    private Doctor doctor;
    private String patientId;
    private long datetime;
    private String symptoms;
    @SerializedName("label")
    private String customerLabel;
    private boolean cancelled;

    public PatientAppointment getPatientAppointment()
    {
        return new PatientAppointment(appointmentId, doctor, patientId, datetime, symptoms, customerLabel, cancelled);
    }
}
