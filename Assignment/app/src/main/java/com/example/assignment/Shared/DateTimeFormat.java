package com.example.assignment.Shared;

import java.time.LocalDate;
import java.util.Date;

public class DateTimeFormat {
    public static String formatDate(Date date)
    {
        return "" + date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
    }

   /* public static String formatDate(LocalDate date)
    {
        return "" + date.getDayOfMonth() + "/" + (date.getMonth()+1) + "/" + date.getYear();
    }*/

    public static String formatDateTime(Date date)
    {
        String d = formatDate(date);
        Date midnight = new Date(date.getYear(), date.getMonth(), date.getDate());
        String time = formatTime(date.getTime() - midnight.getTime());
        return d + " " + time;
    }

    public static String formatTime(long time)
    {
        String hour, minutes;
        int h = (int)(time/1000/60/60);
        int min = (int)(time/1000/60 - h*60);
        if(h < 10)
            hour = "0" + h;
        else hour = "" + h;
        if(min < 10)
            minutes = "0" + min;
        else minutes = "" + min;

        return hour + ":" + minutes;
    }
}
