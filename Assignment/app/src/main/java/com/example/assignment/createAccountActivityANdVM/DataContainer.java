package com.example.assignment.createAccountActivityANdVM;

import android.app.Application;

import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Model.Person;
import com.example.assignment.Model.UserType;
import com.example.assignment.Repository;
import com.example.assignment.Shared.DoctorAppointmentTimes;
import com.example.assignment.localDb.User;

import java.util.List;

//is used so during the create account process, the data collected from each step is preserved somewhere outside the repository until the call to backend
//-when it will not be need of it anymore
public class DataContainer {
    private static DataContainer instance;
    private Person person;
    private UserType type;
    private DoctorAppointmentTimes dat;

    private DataContainer()
    {

    }

    public static synchronized DataContainer getInstance()
    {
        if(instance == null)
            instance = new DataContainer();
        return instance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person, UserType type) {
        this.person = person;
        this.type = type;
    }

    public UserType getUserType() {
        return type;
    }

    public DoctorAppointmentTimes getDat() {
        return dat;
    }

    public void addDat(List<Long> times) {
        dat = new DoctorAppointmentTimes(person.getPersonId(), times);
    }
}
