package com.example.assignment.localDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

@Dao
public interface AppointmentDAO {
    @Insert
    void insertAppointment(AppointmentDB app);

    @Delete
    void deleteAppointment(AppointmentDB app);

    @Query("DELETE FROM appointmentdb")
    void deleteAllAppointments();

    @Query("SELECT * FROM appointmentdb")
    LiveData<List<AppointmentDB>> getAppointments();


    @Query("SELECT * FROM appointmentdb where date >= :currentDay")
    LiveData<List<AppointmentDB>> getActiveAppointments(Long currentDay);


    @Query("SELECT * FROM appointmentdb where date < :currentDay")
    LiveData<List<AppointmentDB>> getPastAppointments(Long currentDay);
}
