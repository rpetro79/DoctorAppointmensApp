package com.example.assignment.localDb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignment.Model.UserType;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String userId;
    private String name;
    private UserType userType;
    private String country;
    private String city;

    public User(String userId, String name, UserType userType, String country, String city) {
        this.userId = userId;
        this.name = name;
        this.userType = userType;
        this.country = country;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
