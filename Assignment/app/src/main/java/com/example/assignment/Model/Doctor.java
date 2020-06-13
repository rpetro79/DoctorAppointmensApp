package com.example.assignment.Model;

import java.io.Serializable;

public class Doctor extends Person implements Serializable {
    private String cvr;
    private String clinicName;
    private String address;
    private String specialization;

    public Doctor(String personId, String ssn, String name, String email, String phoneNo, String cvr, String clinicName, String address, String city, String country, String specialization) {
        super(personId, ssn, name, email, phoneNo, city, country);
        this.cvr = cvr;
        this.clinicName = clinicName;
        this.address = address;
        this.specialization = specialization;
    }

    public Doctor(String personId, String ssn, String name, String email, String phone, String country, String city) {
        super(personId, ssn, name, email, phone, country, city);
    }

    public Doctor(String ssn, String name, String email, String phone, String country, String city) {
        super(ssn, name, email, phone, country, city);
    }

    public String getCountry() {
        return super.getCountry();
    }

    public void setCountry(String country) {
        super.setCountry(country);
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        super.setCity(city);
    }

    public String getCity() {
        return super.getCity();
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCvr() {
        return cvr;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getAddress() {
        return address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSsn(String ssn) {
        super.setSsn(ssn);
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public void setPhone(String phoneNo) {
        super.setPhone(phoneNo);
    }

    public String getSsn() {
        return super.getSsn();
    }

    public String getName() {
        return super.getName();
    }

    public String getEmail() {
        return super.getEmail();
    }

    public String getPhone() {
        return super.getPhone();
    }

    public String getPersonId()
    {
        return super.getPersonId();
    }

    public void setPersonId(String personId)
    {
        super.setPersonId(personId);
    }
}
