package com.example.assignment.Shared;

import org.apache.commons.lang3.StringUtils;

public class Checker {
    public static boolean checkNumber(String number)
    {
        if(StringUtils.containsAny(number, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ><\"\'=+!,@#$%^&*()- ."))
            return false;
        return true;
    }

    public static boolean checkEmail(String email)
    {
        char x = email.charAt(0);
        int i = 1;
        while(x != '@' && i < email.length())
        {
            x = email.charAt(i);
            i++;
        }
        if(i == email.length())
            return false;
        while(x != '.' && i < email.length())
        {
            x = email.charAt(i);
            i++;
        }
        if(i == email.length() || StringUtils.containsAny(email, "><\"\'=+!,#$%^&*()- "))
            return false;
        return true;
    }
}
