package com.example.assignment.Model;

import java.io.Serializable;
import java.util.Date;

public class DoctorAppointment implements Serializable {
    private int appointmentId;
    private String doctorId;
    private Patient patient;
    private Date datetime;
    private String symptoms;
    private boolean cancelled;

    public DoctorAppointment(int appointmentId, Patient patient, String doctorId, long datetime, String symptoms, boolean cancelled)
    {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patient = patient;
        this.datetime = new Date(datetime);
        this.symptoms = symptoms;
        this.cancelled = cancelled;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
