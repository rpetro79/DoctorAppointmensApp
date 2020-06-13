package com.example.assignment.Shared;

import java.util.List;

public class DoctorAppointmentTimes {
    private String doctorId;
    private List<Long> appointmentTimes;

    public DoctorAppointmentTimes(String doctorId, List<Long> times)
    {
        this.doctorId = doctorId;
        this.appointmentTimes = times;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<Long> getAppointmentTimes() {
        return appointmentTimes;
    }

    public void setAppointmentTimes(List<Long> appointmentTimes) {
        this.appointmentTimes = appointmentTimes;
    }
}
