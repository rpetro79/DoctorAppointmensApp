package com.example.assignment.Model;

import java.io.Serializable;

public class Person implements Serializable {
    private String personId;
    private String ssn;
    private String name;
    private String email;
    private String phone;
    private String city;
    private String country;

    public Person(String personId, String ssn, String name, String email, String phone, String country, String city) {
        this.personId = personId;
        this.ssn = ssn;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.city = city;
    }

    public Person(String ssn, String name, String email, String phone, String country, String city) {
        this.ssn = ssn;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.city = city;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSsn() {
        return ssn;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
