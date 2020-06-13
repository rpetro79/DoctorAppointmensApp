package com.example.assignment.Model;


public enum UserType {
    PATIENT(0),
    DOCTOR(1);

    private final int code;

    UserType(int value) {
        this.code = value;
    }
    public int getCode() {
        return code;
    }
}
