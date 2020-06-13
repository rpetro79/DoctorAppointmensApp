package com.example.assignment.localDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, AppointmentDB.class}, version = 7, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;
    public abstract UserDAO userDAO();
    public abstract AppointmentDAO appointmentDAO();

    public static synchronized LocalDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDatabase.class, "local_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

