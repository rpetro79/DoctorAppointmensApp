package com.example.assignment.Model;

import java.util.Date;

public class Appointment {
    private int appointmentId;
    private String patientId;
    private String doctorId ;
    private long datetime;
    private String symptoms;
    private String label;
    private boolean cancelled;

    public Appointment(String patientId, String doctorId, long datetime, String symptoms, String label, boolean cancelled)
    {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.symptoms = symptoms;
        this.datetime = datetime;
        this.label = label;
        this.cancelled = cancelled;
    }

    public Appointment(int appointmentId, String patientId, String doctorId, long datetime, String symptoms, String label, boolean cancelled)
    {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.symptoms = symptoms;
        this.datetime = datetime;
        this.label = label;
        this.cancelled = cancelled;
    }

    public Appointment(PatientAppointment app)
    {
        this.appointmentId = app.getAppointmentId();
        this.doctorId = app.getDoctor().getPersonId();
        this.patientId = app.getPatientId();
        this.datetime = app.getDatetime().getTime();
        this.symptoms = app.getSymptoms();
        this.label = app.getCustomerLabel();
        this.cancelled = app.isCancelled();
    }

    public Appointment(DoctorAppointment app)
    {
        this.appointmentId = app.getAppointmentId();
        this.doctorId = app.getDoctorId();
        this.patientId = app.getPatient().getPersonId();
        this.datetime = app.getDatetime().getTime();
        this.symptoms = app.getSymptoms();
        this.cancelled = app.isCancelled();
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
