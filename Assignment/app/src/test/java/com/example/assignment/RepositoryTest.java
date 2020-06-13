package com.example.assignment;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.UserType;
import com.example.assignment.apiConnection.ApiCallHandler;
import com.example.assignment.firebase.FirebaseConnection;
import com.example.assignment.localDb.AppointmentDAO;
import com.example.assignment.localDb.AppointmentDB;
import com.example.assignment.localDb.User;
import com.example.assignment.localDb.UserDAO;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class RepositoryTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private UserDAO userDAO;

    @Mock
    private AppointmentDAO appointmentDAO;

    @Mock
    private FirebaseConnection firebaseConnection;

    @Mock
    private ApiCallHandler api;

    private Repository repo;

    @Before
    public void setup()
    {
        List<User> userList = new ArrayList<>();
        userList.add(new User("user1", "User name", UserType.PATIENT, "Denmark", "Horsens"));
        LiveData<List<User>> users = new MutableLiveData<>(userList);
        when(userDAO.getUsers()).thenReturn(users);
        List<AppointmentDB> appointmentList = new ArrayList<>();
        appointmentList.add(new AppointmentDB(1, "Dentist", new Date(120, 6, 15), "Vejlevej 15, Horsens, Denmark", false));
        appointmentList.add(new AppointmentDB(2, "Dentist", new Date(120, 6, 22), "Vejlevej 15, Horsens, Denmark", false));
        LiveData<List<AppointmentDB>> appointments = new MutableLiveData<>(appointmentList);
        when(appointmentDAO.getActiveAppointments(any(Long.class))).thenReturn(appointments);

        repo = new Repository(userDAO, appointmentDAO, firebaseConnection, api);
    }

    @Test
    public void testCreation() {
        assertNotNull(repo);
    }

    @Test
    public void getUsers() {
        LiveData<List<User>> users = repo.getUsers();
        assertNotNull(users);
        assertNotNull(users.getValue());
        assertEquals(users.getValue().size(), 1);
        assertEquals(users.getValue().get(0).getName(), "User name");
        assertEquals(users.getValue().get(0).getUserId(), "user1");
        assertEquals(users.getValue().get(0).getUserType(), UserType.PATIENT);
        assertEquals(users.getValue().get(0).getCountry(), "Denmark");
        assertEquals(users.getValue().get(0).getCity(), "Horsens");
    }

    @Test
    public void getActiveAppointmentsDB() {
        LiveData<List<AppointmentDB>> appointments = repo.getActiveAppointmentsDB();
        assertNotNull(appointments);
        assertNotNull(appointments.getValue());
        assertEquals(appointments.getValue().size(), 2);
        assertEquals(appointments.getValue().get(0).getId(), 1);
        assertEquals(appointments.getValue().get(0).getLabel(), "Dentist");
        assertEquals(appointments.getValue().get(0).getDetails(), "Vejlevej 15, Horsens, Denmark");
        assertFalse(appointments.getValue().get(0).getCancelled());
        assertEquals(appointments.getValue().get(0).getDate(), new Date(120, 6, 15));

        assertEquals(appointments.getValue().get(1).getId(), 2);
        assertEquals(appointments.getValue().get(1).getLabel(), "Dentist");
        assertEquals(appointments.getValue().get(1).getDetails(), "Vejlevej 15, Horsens, Denmark");
        assertFalse(appointments.getValue().get(1).getCancelled());
        assertEquals(appointments.getValue().get(1).getDate(), new Date(120, 6, 22));
    }

    @Test
    public void getPastAppointmentsDB() {
        LiveData<List<AppointmentDB>> appointments = repo.getPastAppointmentsDB();
        assertNotNull(appointments);
        assertNotNull(appointments.getValue());
        assertEquals(appointments.getValue().size(), 0);
    }

    @Test
    public void logIn() {
        repo.logIn("email", "pass", UserType.PATIENT);
        verify(firebaseConnection, times(1)).signIn("email", "pass");
    }

    @Test
    public void updateData() {
        List<AppointmentDB> appointmentList = new ArrayList<>();
        appointmentList.add(new AppointmentDB(1, "Dentist", new Date(120, 6, 15), "Vejlevej 15, Horsens, Denmark", false));
        LiveData<List<AppointmentDB>> appointments = new MutableLiveData<>(appointmentList);
        when(appointmentDAO.getActiveAppointments(any(Long.class))).thenReturn(appointments);

        assertNotNull(appointments);
        assertNotNull(appointments.getValue());
        assertEquals(appointments.getValue().size(), 1);
        assertEquals(appointments.getValue().get(0).getId(), 1);
        assertEquals(appointments.getValue().get(0).getLabel(), "Dentist");
        assertEquals(appointments.getValue().get(0).getDetails(), "Vejlevej 15, Horsens, Denmark");
        assertFalse(appointments.getValue().get(0).getCancelled());
        assertEquals(appointments.getValue().get(0).getDate(), new Date(120, 6, 15));
    }
}