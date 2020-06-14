package com.example.assignment;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.Doctor;
import com.example.assignment.Model.DoctorAppointment;
import com.example.assignment.Model.Patient;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.Model.Person;
import com.example.assignment.Model.UserType;
import com.example.assignment.apiConnection.ApiCallHandler;
import com.example.assignment.createAccountActivityANdVM.DataContainer;
import com.example.assignment.firebase.FirebaseConnection;
import com.example.assignment.localDb.AppointmentDAO;
import com.example.assignment.localDb.AppointmentDB;
import com.example.assignment.localDb.LocalDatabase;
import com.example.assignment.localDb.User;
import com.example.assignment.localDb.UserDAO;
import com.google.firebase.auth.FirebaseUser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Repository {
    private static Repository instance;
    private UserDAO userDAO;
    private AppointmentDAO appointmentDAO;
    private LiveData<List<User>> users;
    private LiveData<List<AppointmentDB>> activeAppointmentsDB;
    private MutableLiveData<List<AppointmentDB>> pastAppointmentsDB;
    private FirebaseConnection firebaseConnection;
    private ApiCallHandler api;
    private PropertyChangeSupport pcSupport;
    private User user;
    private UserType userType;

    private Repository(Application application)
    {
        LocalDatabase database = LocalDatabase.getInstance(application);
        userDAO = database.userDAO();
        appointmentDAO = database.appointmentDAO();

        updateData();

        pcSupport = new PropertyChangeSupport(this);

        pastAppointmentsDB = new MutableLiveData<List<AppointmentDB>>(new ArrayList<AppointmentDB>());

        firebaseConnection = FirebaseConnection.getInstance();
        addFirebaseListeners();

        api = ApiCallHandler.getInstance();
        addApiListeners();

        checkUserLogInOnFirebase();
    }

    private void addFirebaseListeners()
    {
        firebaseConnection.addListener("userCreated", this :: userCreated);
        firebaseConnection.addListener("userAuthenticated", this :: userAuthenticated);
        firebaseConnection.addListener("authenticationError", this :: authenticationError);
    }

    private void addApiListeners()
    {
        api.addListener("postPatientOkay", this :: postPatientOkay);
        api.addListener("postPatientFail", this :: postPatientFail);
        api.addListener("gotPatientUpcomingAppointments", this :: gotPatientUpcomingAppointments);
        api.addListener("gotPatientAppointmentFail", this :: gotPatientAppointmentFail);
        api.addListener("gotPatientPastAppointments", this :: gotPatientPastAppointments);
        api.addListener("countries", this :: gotCountries);
        api.addListener("regions", this :: gotRegions);
        api.addListener("cities", this :: gotCities);
        api.addListener("gotPatient", this :: gotPatient);
        api.addListener("gotDoctor", this :: gotDoctor);
        api.addListener("postDoctorOkay", this :: postDoctorOkay);
        api.addListener("postDoctorFail", this :: postDoctorFail);
        api.addListener("postDATFail", this :: postDATFail);
        api.addListener("postDATOkay", this :: postDATOkay);
        api.addListener("gotSpecializations", this :: gotSpecializations);
        api.addListener("gotSearchDoctors", this :: gotSearchDoctors);
        api.addListener("gotSearchDoctorsFail", this :: gotSearchDoctorsFail);
        api.addListener("gotDATForDate", this :: gotDATForDate);
        api.addListener("postAppointmentOkay", this :: postAppointmentOkay);
        api.addListener("postAppointmentFail", this :: postAppointmentFail);
        api.addListener("gotAppointmentAfterPost", this :: gotAppointmentAfterPost);
        api.addListener("gotPatientAppointment", this :: gotPatientAppointment);
        api.addListener("putPatientAppointmentOkay", this :: putPatientAppointmentOkay);
        api.addListener("gotSpecializationsFail", this :: gotSpecializationsFail);
        api.addListener("gotDoctorAppointmentsForDay", this :: gotDoctorAppointmentsForDay);
        api.addListener("gotDoctorAppointmentsForDayFail", this :: gotDoctorAppointmentsForDayFail);
        api.addListener("gotDoctorAppointment", this :: gotDoctorAppointment);
        api.addListener("gotDoctorAppointmentFail", this :: gotDoctorAppointmentFail);
    }

    //only for testing
    public Repository(UserDAO userDAO, AppointmentDAO appointmentDAO, FirebaseConnection firebaseConnection, ApiCallHandler api) {
        this.userDAO = userDAO;
        this.appointmentDAO = appointmentDAO;
        this.firebaseConnection = firebaseConnection;
        this.api = api;

        pcSupport = new PropertyChangeSupport(this);
        pastAppointmentsDB = new MutableLiveData<List<AppointmentDB>>(new ArrayList<AppointmentDB>());

        updateData();

        //addApiListeners();
        //addFirebaseListeners();
        //checkUserLogInOnFirebase();
    }

    private void gotDoctorAppointmentFail(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotDoctorAppointmentFail", null, propertyChangeEvent.getNewValue());
    }

    private void gotDoctorAppointment(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotDoctorAppointment", null, propertyChangeEvent.getNewValue());
    }

    private void gotDoctorAppointmentsForDayFail(PropertyChangeEvent propertyChangeEvent) {

    }

    private void gotDoctorAppointmentsForDay(PropertyChangeEvent propertyChangeEvent) {
        List<DoctorAppointment> appointments = (List<DoctorAppointment>)propertyChangeEvent.getNewValue();
        new InsertAppointmentsAsync(appointmentDAO, AppointmentDB.toAppointmentsDbFromDoctorAppointments(appointments)).execute();
    }

    private void gotDoctor(PropertyChangeEvent propertyChangeEvent) {
        Doctor d = (Doctor)propertyChangeEvent.getNewValue();
        user = new User(d.getPersonId(), d.getName(), UserType.DOCTOR, d.getCountry(), d.getCity());
        new UserLogInAsync(userDAO, user).execute();
        Calendar cal = Calendar.getInstance();
        Date date = new Date(cal.get(Calendar.YEAR)-1900, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        api.getDoctorAppointmentsForDay(firebaseConnection.getUserId(), date.getTime());
    }

    private void gotSearchDoctorsFail(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotSearchDoctorsFail", null, null);
    }

    private void gotPatientAppointmentFail(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotPatientAppointmentFail", null, null);
    }

    private void gotSpecializationsFail(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotSpecializationsFail", null, null);
    }

    private void putPatientAppointmentOkay(PropertyChangeEvent propertyChangeEvent) {
        Appointment app = (Appointment) propertyChangeEvent.getNewValue();
        updateAppointment(app);
        pcSupport.firePropertyChange("putPatientAppointmentOkay", null, propertyChangeEvent.getNewValue());
    }

    private void gotPatientAppointment(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotPatientAppointment", null, propertyChangeEvent.getNewValue());
    }

    private void gotAppointmentAfterPost(PropertyChangeEvent propertyChangeEvent) {
        List<AppointmentDB> appointments = activeAppointmentsDB.getValue();
        appointments.add(AppointmentDB.toAppointmentDb((PatientAppointment) propertyChangeEvent.getNewValue()));
        new InsertAppointmentsAsync(appointmentDAO, appointments).execute();
    }

    private void postAppointmentFail(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("postAppointmentFail", null, null);
    }

    private void postAppointmentOkay(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("postAppointmentOkay", null, null);
        Appointment ap = (Appointment) propertyChangeEvent.getNewValue();
        api.getPatientAppointmentAfterPost(ap.getAppointmentId());
    }

    private void getPatientUpcomingAppointments() {
        api.getPatientUpcomingAppointments(firebaseConnection.getUserId());
    }

    private void gotDATForDate(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotDATForDate", null, propertyChangeEvent.getNewValue());
    }

    private void gotSearchDoctors(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotSearchDoctors", null, propertyChangeEvent.getNewValue());
    }

    private void gotSpecializations(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotSpecializations", null, propertyChangeEvent.getNewValue());
    }

    private void postDATOkay(PropertyChangeEvent propertyChangeEvent) {
    }

    private void postDATFail(PropertyChangeEvent propertyChangeEvent) {
    }

    private void postDoctorFail(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("errorPostingUser", null, null);
    }

    private void postDoctorOkay(PropertyChangeEvent propertyChangeEvent) {
        DataContainer dc = DataContainer.getInstance();
        dc.getDat().setDoctorId(dc.getPerson().getPersonId());
        api.postDAT(dc.getDat());
        new UserLogInAsync(userDAO, user).execute();
    }

    private void gotPatientPastAppointments(PropertyChangeEvent propertyChangeEvent) {
        List<PatientAppointment> appointments = (List<PatientAppointment>)propertyChangeEvent.getNewValue();
        pastAppointmentsDB.postValue(AppointmentDB.toAppointmentsDb(appointments));
    }

    private void gotPatient(PropertyChangeEvent propertyChangeEvent) {
        Patient p = (Patient)propertyChangeEvent.getNewValue();
        user = new User(p.getPersonId(), p.getName(), UserType.PATIENT, p.getCountry(), p.getCity());
        new UserLogInAsync(userDAO, user).execute();
        api.getPatientUpcomingAppointments(firebaseConnection.getUserId());
    }

    private void gotCountries(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotCountries", null, propertyChangeEvent.getNewValue());
    }

    private void gotRegions(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotRegions", null, propertyChangeEvent.getNewValue());
    }

    private void gotCities(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("gotCities", null, propertyChangeEvent.getNewValue());
    }

    private void gotPatientUpcomingAppointments(PropertyChangeEvent propertyChangeEvent) {
        List<PatientAppointment> appointments = (List<PatientAppointment>)propertyChangeEvent.getNewValue();
        new InsertAppointmentsAsync(appointmentDAO, AppointmentDB.toAppointmentsDb(appointments)).execute();
    }

    private void postPatientFail(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("errorPostingUser", null, null);
    }

    private void postPatientOkay(PropertyChangeEvent propertyChangeEvent) {
        DataContainer dc = DataContainer.getInstance();
        new UserLogInAsync(userDAO, user).execute();
        api.getPatientUpcomingAppointments(firebaseConnection.getUserId());
    }

    //for security reasons
    private void checkUserLogInOnFirebase() {
        if(firebaseConnection.getUser() == null)
        {
            new LogUserOutAsync(userDAO, appointmentDAO).execute();
        }
    }

    private void authenticationError(PropertyChangeEvent propertyChangeEvent) {
        pcSupport.firePropertyChange("authenticationError", null, null);
    }

    private void userAuthenticated(PropertyChangeEvent propertyChangeEvent) {
        if(userType == UserType.PATIENT)
            api.getPatient(firebaseConnection.getUserId());
        else api.getDoctor(firebaseConnection.getUserId());
    }

    private void userCreated(PropertyChangeEvent propertyChangeEvent) {
        DataContainer container = DataContainer.getInstance();
        container.getPerson().setPersonId(firebaseConnection.getUserId());
        if(container.getUserType() == UserType.PATIENT)
        {
            Patient p = (Patient)container.getPerson();
            api.postPatient(p);
        }
        else
        {
            Doctor d = (Doctor)container.getPerson();
            api.postDoctor(d);
        }
        user = new User(container.getPerson().getPersonId(), container.getPerson().getName(), container.getUserType(), container.getPerson().getCountry(), container.getPerson().getCity());
    }

    //to initialize the repository -- called from MainActivityViewModel everytime
    public static synchronized Repository getInstance(Application application)
    {
        if(instance == null)
            instance = new Repository(application);
        return instance;
    }

    //called every other time
    public static Repository getInstance() {
        return instance;
    }

    //in reality, only one user;
    //purpose: so the activities can interact with the user based on the data known about them
    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<List<AppointmentDB>> getActiveAppointmentsDB() {
        return activeAppointmentsDB;
    }

    public LiveData<List<AppointmentDB>> getPastAppointmentsDB() {
        return pastAppointmentsDB;
    }

    //data in the local database is updated, data from the backend is retrieved
    public void logIn(String email, String password, UserType userType) {
        firebaseConnection.signIn(email, password);
        this.userType = userType;
    }

    //for create account and search
    public void getAllCountries() {
        api.getCountries();
    }

    //for create account and search
    public void getCities(String country, String region)
    {
        api.getCities(country, region);
    }

    //for create account and search
    public void getRegions(String country)
    {
        api.getRegions(country);
    }

    //for doctor create account
    public void getSpecializations() {
        api.getSpecializations();
    }

    //creating the account on the backend -- the rest is just like log in
    public void createAccount(String email, String password) {
        firebaseConnection.createAccount(email, password);
    }

    //to the local db
    public void updateData()
    {
        Calendar cal = Calendar.getInstance();
        users = userDAO.getUsers();
        activeAppointmentsDB = appointmentDAO.getActiveAppointments(cal.getTimeInMillis());
        try{
            if(users.getValue().size() > 0)
                user = users.getValue().get(0);
        }catch (NullPointerException e)
        {
            user = null;
         //   activeAppointmentsDB = new MutableLiveData<>();
        }
    }

    //empties the local db
    public void logOut() {
        firebaseConnection.userSignOut();
        new LogUserOutAsync(userDAO, appointmentDAO).execute();
    }

    public void fetchPastAppointments() {
        api.getPatientPastAppointments(firebaseConnection.getUserId());
    }

    public void addListener(String name, PropertyChangeListener listener) {
        if (name == null)
            pcSupport.addPropertyChangeListener(listener);
        else pcSupport.addPropertyChangeListener(name, listener);
    }

    public void getDoctors(String specialization, String country, String city) {
        api.getDoctors(specialization, country, city);
    }

    public void getDATForDay(String doctorId, long time) {
        api.getDATForDay(doctorId, time);
    }

    public void postAppointment(Appointment appointment) {
        api.postAppointment(appointment);
    }

    public void getPatientAppointment(int id) {
        api.getPatientAppointment(id);
    }

    public void putAppointment(Appointment appointment) {
        api.putAppointment(appointment);
    }

    public void updateAppointment(Appointment patientAppointment) {
        List<AppointmentDB> appointments = activeAppointmentsDB.getValue();
        for (int i = 0; i < appointments.size(); ++i)
        {
            if(appointments.get(i).getId() == patientAppointment.getAppointmentId())
            {
                appointments.get(i).copyChanges(patientAppointment);
                break;
            }
        }
        new InsertAppointmentsAsync(appointmentDAO, appointments).execute();
    }

    public void getDoctorAppointmentsForDay(Date date) {
        api.getDoctorAppointmentsForDay(user.getUserId(), date.getTime());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void getDoctorAppointment(int id) {
        api.getDoctorAppointment(id);
    }

    private static class LogUserOutAsync extends AsyncTask<Void,Void,Void> {
        private UserDAO userDao;
        private AppointmentDAO appointmentDao;

        private LogUserOutAsync(UserDAO userDao, AppointmentDAO appDao) {
            this.userDao = userDao;
            this.appointmentDao = appDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.delete();
            appointmentDao.deleteAllAppointments();
            return null;
        }
    }

    private static class UserLogInAsync extends AsyncTask<Void,Void,Void> {
        private UserDAO userDao;
        private User user;

        private UserLogInAsync(UserDAO userDao, User user) {
            this.userDao = userDao;
            this.user = user;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.delete();
            userDao.insert(user);
            return null;
        }
    }

    private static class InsertAppointmentsAsync extends AsyncTask<Void,Void,Void> {
        private AppointmentDAO appDao;
        private List<AppointmentDB> appointments;

        private InsertAppointmentsAsync(AppointmentDAO appDao, List<AppointmentDB> apps) {
            this.appDao = appDao;
            this.appointments = apps;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.deleteAllAppointments();
            for(int i = 0; i < appointments.size(); ++i)
            {
                appDao.insertAppointment(appointments.get(i));
            }
            return null;
        }
    }
}
