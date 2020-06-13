package com.example.assignment.localDb;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.assignment.Model.UserType;

import java.util.Date;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        Log.e("db values", ""+ value);
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromUserType(UserType value) {
        return value == null ? null : value.toString();
    }

    @TypeConverter
    public static UserType dateToTimestamp(String value) {
        return value == null ? null : UserType.valueOf(value);
    }
}
