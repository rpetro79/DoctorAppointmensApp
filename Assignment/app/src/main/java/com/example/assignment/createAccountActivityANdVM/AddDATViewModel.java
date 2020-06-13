package com.example.assignment.createAccountActivityANdVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.R;
import com.example.assignment.Repository;
import com.example.assignment.Shared.Checker;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddDATViewModel extends AndroidViewModel {
    private List<Long> times;

    public AddDATViewModel(@NonNull Application application) {
        super(application);
        times = new ArrayList<Long>();
    }

    public boolean addTime(String hoursString, String minuteString)
    {
        int hour, minutes;
        if(Checker.checkNumber(hoursString))
            hour = Integer.parseInt(hoursString);
        else {
            return false;
        }
        if(Checker.checkNumber(minuteString))
            minutes = Integer.parseInt(minuteString);
        else
        {
            return false;
        }

        long time = (hour*60 + minutes)*60*1000;
        for(int i = 0; i < times.size(); ++i)
            if(times.get(i) == time)
                return false;
        times.add(time);
        return true;
    }

    public void undo() {
        times.remove(times.size()-1);
    }

    public void next() {
        DataContainer.getInstance().addDat(times);
    }
}
