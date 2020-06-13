package com.example.assignment.AppointmentsViews;

import com.example.assignment.localDb.AppointmentDB;

import java.util.List;

public interface AppointmentFragmentActivityInterface {
    List<AppointmentDB> getUpcomingAppointments();
    List<AppointmentDB> getPastAppointments();
    List<AppointmentDB> getDaysAppointments();
    void getAppointmentDetails(AppointmentDB appointmentDB);
}
