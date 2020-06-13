package com.example.assignment.localDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Query("DELETE FROM User")
    void delete();

    @Query("SELECT * FROM User")
    LiveData<List<User>> getUsers();
}
