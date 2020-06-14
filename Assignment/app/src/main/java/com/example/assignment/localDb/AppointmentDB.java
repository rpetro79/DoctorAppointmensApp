package com.example.assignment.localDb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Model.PatientAppointment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class AppointmentDB {
    @PrimaryKey
    private int id;
    private String label;
    private Date date;
    private String details;
    private boolean cancelled;



    public AppointmentDB(int id, String label, Date date, String details, boolean cancelled) {
        this.id = id;
        this.label = label;
        this.date = date;
        this.details = details;
        this.cancelled = cancelled;
    }

    public boolean getCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLabel() {
        return label;
    }

    public Date getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public static List<AppointmentDB> toAppointmentsDb(List<PatientAppointment> list)
    {
        List<AppointmentDB> toReturn = new ArrayList<AppointmentDB>();
        for(int i = 0; i < list.size(); ++i)
            toReturn.add(new AppointmentDB(list.get(i).getAppointmentId(), list.get(i).getCustomerLabel(), list.get(i).getDatetime(), list.get(i).getDoctor().getAddress(), list.get(i).isCancelled()));
        return toReturn;
    }

    public static List<AppointmentDB> toAppointmentsDbFromDoctorAppointments(List<DoctorAppointment> list)
    {
        List<AppointmentDB> toReturn = new ArrayList<AppointmentDB>();
        for(int i = 0; i < list.size(); ++i)
            toReturn.add(new AppointmentDB(list.get(i).getAppointmentId(), list.get(i).getPatient().getName(), list.get(i).getDatetime(), list.get(i).getSymptoms(), list.get(i).isCancelled()));
        return toReturn;
    }

    public static AppointmentDB toAppointmentDb(PatientAppointment ap)
    {
        return new AppointmentDB(ap.getAppointmentId(), ap.getCustomerLabel(), ap.getDatetime(), ap.getDoctor().getAddress() + ", " + ap.getDoctor().getCity() + ", " + ap.getDoctor().getCountry(), ap.isCancelled());
    }

    public void copyChanges(Appointment ap)
    {
        this.cancelled = ap.isCancelled();
        this.label = ap.getLabel();
        this.date = new Date(ap.getDatetime());
    }
}
