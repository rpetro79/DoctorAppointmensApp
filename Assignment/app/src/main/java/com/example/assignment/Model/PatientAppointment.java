package com.example.assignment.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class PatientAppointment implements Serializable {
    private int appointmentId;
    private Doctor doctor;
    private String patientId;
    private Date datetime;
    private String symptoms;
    private String customerLabel;
    private boolean cancelled;

    public PatientAppointment(int appointmentId, Doctor doctor, String patientId, long datetime, String symptoms, String label, boolean cancelled)
    {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patientId = patientId;
        this.datetime = new Date(datetime);
        this.symptoms = symptoms;
        this.customerLabel = label;
        this.cancelled = cancelled;
    }

    public PatientAppointment(Doctor doctor, String patientId, long datetime, String symptoms, String label, boolean cancelled)
    {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patientId = patientId;
        this.datetime = new Date(datetime);
        this.symptoms = symptoms;
        this.customerLabel = label;
        this.cancelled = cancelled;
    }

    public PatientAppointment(int appointmentId, Doctor doctor, String patientId, Date datetime, String symptoms, String label, boolean cancelled)
    {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patientId = patientId;
        this.datetime = datetime;
        this.symptoms = symptoms;
        this.customerLabel = label;
        this.cancelled = cancelled;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getCustomerLabel() {
        return customerLabel;
    }

    public void setCustomerLabel(String customerLabel) {
        this.customerLabel = customerLabel;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Appointment toAppointment() {
        return new Appointment(appointmentId, patientId, doctor.getPersonId(), datetime.getTime(), symptoms, customerLabel, cancelled);
    }

    public void copyChanges(Appointment app) {
        this.customerLabel = app.getLabel();
        this.datetime = new Date(app.getDatetime());
        this.cancelled = app.isCancelled();
        this.symptoms = symptoms;
    }
}
