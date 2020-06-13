package com.example.assignment.Shared;

public interface EditAppointmentInterface {
    void getDATForDay(long date);
    void sendAppointment(long datetime, String symptoms, String label);
}
