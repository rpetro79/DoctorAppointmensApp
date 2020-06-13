package com.example.assignment.Model;

import java.io.Serializable;
import java.util.Date;

public class Patient extends Person implements Serializable {
    private Date birthday;

    public Patient(String personId, String ssn, String name, String email, String phoneNo, Date birthday, String city, String country) {
        super(personId, ssn, name, email, phoneNo, country, city);
        this.birthday = birthday;
    }

    public Patient(String personId, String ssn, String name, String email, String phone, String country, String city) {
        super(personId, ssn, name, email, phone, country, city);
    }

    public Patient(String ssn, String name, String email, String phone, String country, String city) {
        super(ssn, name, email, phone, country, city);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCity(String city) {
        super.setCity(city);
    }

    public String getCity() {
        return super.getCity();
    }

    public String getCountry() {
        return super.getCountry();
    }

    public void setCountry(String country) {
        super.setCountry(country);
    }

    public Date getBirthday() {
        return birthday;
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

    public int calculateAge(Date date)
    {
        int age = date.getYear() - this.birthday.getYear();
        if(birthday.getMonth() > date.getMonth() || (birthday.getMonth() == date.getMonth() && birthday.getDay() > date.getDay()))
            age--;
        return age;
    }
}
