package com.example.assignment.apiConnection.ApiModel;

import com.example.assignment.Model.Patient;

import java.util.Date;

public class ApiPatient {
    private String personId;
    private String ssn;
    private String name;
    private String email;
    private String phone;
    private String country;
    private String city;
    private long birthday;

    public Patient getPatient()
    {
        return new Patient(personId, ssn, name, email, phone, new Date(birthday), city, country);
    }

    public void fromPatient(Patient patient)
    {
        this.personId = patient.getPersonId();
        this.ssn = patient.getSsn();
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        this.birthday = patient.getBirthday().getTime();
        this.city = patient.getCity();
        this.country = patient.getCountry();
    }
}
