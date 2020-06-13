package com.example.assignment.apiConnection;

import android.util.Log;
import android.view.View;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.Doctor;
import com.example.assignment.Model.Patient;
import com.example.assignment.Model.PatientAppointment;
import com.example.assignment.Shared.DoctorAppointmentTimes;
import com.example.assignment.apiConnection.ApiModel.ApiDoctorAppointment;
import com.example.assignment.apiConnection.ApiModel.ApiPatient;
import com.example.assignment.apiConnection.ApiModel.ApiPatientAppointment;
import com.example.assignment.apiConnection.ApiModel.ServiceGenerator;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCallHandler {
    private static ApiCallHandler instance;
    private API api;
    private PropertyChangeSupport pcSupport;

    private ApiCallHandler()
    {
        api = ServiceGenerator.getApi();
        pcSupport = new PropertyChangeSupport(this);
    }

    public static ApiCallHandler getInstance()
    {
        if(instance == null)
            instance = new ApiCallHandler();
        return instance;
    }

    public void addListener(String name, PropertyChangeListener listener) {
        if (name == null)
            pcSupport.addPropertyChangeListener(listener);
        else pcSupport.addPropertyChangeListener(name, listener);
    }

    public void postPatient(Patient patient)
    {
        ApiPatient apiPatient = new ApiPatient();
        apiPatient.fromPatient(patient);
        Call<Void> call = api.postPatient(apiPatient);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() >= 200 && response.code() <  300) {
                    pcSupport.firePropertyChange("postPatientOkay", null, null);
                }
                else pcSupport.firePropertyChange("postPatientFail", null, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                pcSupport.firePropertyChange("postPatientFail", null, null);
            }
        });
    }

    public void getPatientUpcomingAppointments(String userId)
    {
        Calendar c = Calendar.getInstance();
        long x = c.getTimeInMillis();
        Call<List<ApiPatientAppointment>> call = api.getPatientUpcomingAppointments(userId, x);
        call.enqueue(new Callback<List<ApiPatientAppointment>>() {
            @Override
            public void onResponse(Call<List<ApiPatientAppointment>> call, Response<List<ApiPatientAppointment>> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotPatientUpcomingAppointments", null, ListConverters.toPatientAppointmentList(response.body()));
                }
                else pcSupport.firePropertyChange("gotPatientUpcomingAppointmentsFail", null, null);
            }

            @Override
            public void onFailure(Call<List<ApiPatientAppointment>> call, Throwable t) {
                pcSupport.firePropertyChange("gotPatientUpcomingAppointmentsFail", null, null);
            }
        });
    }

    public void getCountries() {
        Call<List<String>> call = api.getCountries();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("countries", null, response.body());
                }
                else pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    public void getRegions(String country) {
        Call<List<String>> call = api.getRegions(country);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("regions", null, response.body());
                }
                else pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    public void getCities(String country, String region) {
        Call<List<String>> call = api.getCities(country, region);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("cities", null, response.body());
                }
                else pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                pcSupport.firePropertyChange("connectionError", null, null);
            }
        });
    }

    public void getPatient(String userId) {
        Call<ApiPatient> call = api.getPatient(userId);
        call.enqueue(new Callback<ApiPatient>() {
            @Override
            public void onResponse(Call<ApiPatient> call, Response<ApiPatient> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotPatient", null, response.body().getPatient());
                }
                else pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<ApiPatient> call, Throwable t) {
                pcSupport.firePropertyChange("connectionError", null, null);
            }
        });
    }

    public void getPatientPastAppointments(String userId) {
        Calendar c = Calendar.getInstance();
        long x = c.getTimeInMillis();
        Call<List<ApiPatientAppointment>> call = api.getPatientPastAppointments(userId, x);
        call.enqueue(new Callback<List<ApiPatientAppointment>>() {
            @Override
            public void onResponse(Call<List<ApiPatientAppointment>> call, Response<List<ApiPatientAppointment>> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotPatientPastAppointments", null, ListConverters.toPatientAppointmentList(response.body()));
                }
                else pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<List<ApiPatientAppointment>> call, Throwable t) {
                pcSupport.firePropertyChange("gotPatientPastAppointmentsFail", null, null);
            }
        });
    }

    public void postDoctor(Doctor d) {
        Call<Void> call = api.postDoctor(d);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() >= 200 && response.code() <  300) {
                    pcSupport.firePropertyChange("postDoctorOkay", null, null);
                }
                else
                    pcSupport.firePropertyChange("postDoctorFail", null, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                pcSupport.firePropertyChange("postDoctorFail", null, null);
            }
        });
    }

    public void postDAT(DoctorAppointmentTimes dat) {
        Call<Void> call = api.postDAT(dat);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() >= 200 && response.code() <  300) {
                    pcSupport.firePropertyChange("postDATOkay", null, null);
                }
                else
                    pcSupport.firePropertyChange("postDATFail", null, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                pcSupport.firePropertyChange("postDATFail", null, null);
            }
        });
    }

    public void getSpecializations() {
        Call<List<String>> call = api.getSpecializations();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() >= 200 && response.code() <  300) {
                    pcSupport.firePropertyChange("gotSpecializations", null, response.body());
                }
                else
                    pcSupport.firePropertyChange("gotSpecializationsFail", null, null);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                pcSupport.firePropertyChange("gotSpecializationsFail", null, null);
            }
        });
    }

    public void getDoctor(String doctorId) {
        Call<Doctor> call = api.getDoctor(doctorId);
        call.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotDoctor", null, response.body());
                }
                else pcSupport.firePropertyChange("gotDoctorFail", null, null);
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                pcSupport.firePropertyChange("gotDoctorFail", null, null);
            }
        });
    }

    public void getDoctors(String specialization, String country, String city) {
        Call<List<Doctor>> call = api.getDoctors(specialization, country, city);
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if (response.code() == 200) {
                    List<Doctor> resp = response.body();
                    pcSupport.firePropertyChange("gotSearchDoctors", null, response.body());
                }
                else pcSupport.firePropertyChange("gotSearchDoctorsFail", null, null);
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                pcSupport.firePropertyChange("gotSearchDoctorsFail", null, null);
            }
        });
    }

    public void getDATForDay(String doctorId, long time) {
        Call<List<Long>> call = api.getDoctorAppointmentTimes(doctorId, time);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotDATForDate", null, response.body());
                }
                else pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                pcSupport.firePropertyChange("connectionError", null, null);
            }
        });
    }

    public void postAppointment(Appointment appointment) {
        Call<Appointment> call = api.postAppointment(appointment);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.code() >= 200 && response.code() <  300) {
                    pcSupport.firePropertyChange("postAppointmentOkay", null, response.body());
                }
                else pcSupport.firePropertyChange("postAppointmentFail", null, null);
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                pcSupport.firePropertyChange("postAppointmentFail", null, null);
            }
        });
    }

    public void getPatientAppointmentAfterPost(int appointmentId) {
        Call<ApiPatientAppointment> call = api.getPatientAppointment(appointmentId);
        call.enqueue(new Callback<ApiPatientAppointment>() {
            @Override
            public void onResponse(Call<ApiPatientAppointment> call, Response<ApiPatientAppointment> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotAppointmentAfterPost", null, response.body().getPatientAppointment());
                }
                else
                    pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<ApiPatientAppointment> call, Throwable t) {
                pcSupport.firePropertyChange("connectionError", null, null);
            }
        });
    }

    public void getPatientAppointment(int id) {
        Call<ApiPatientAppointment> call = api.getPatientAppointment(id);
        call.enqueue(new Callback<ApiPatientAppointment>() {
            @Override
            public void onResponse(Call<ApiPatientAppointment> call, Response<ApiPatientAppointment> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotPatientAppointment", null, response.body().getPatientAppointment());
                }
                else
                    pcSupport.firePropertyChange("gotPatientAppointmentFail", null, null);
            }

            @Override
            public void onFailure(Call<ApiPatientAppointment> call, Throwable t) {
                pcSupport.firePropertyChange("gotPatientAppointmentFail", null, null);
            }
        });
    }

    public void putAppointment(Appointment appointment) {
        Call<Appointment> call = api.putAppointment(appointment);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.code() >= 200 && response.code() <  300) {
                    pcSupport.firePropertyChange("putPatientAppointmentOkay", null, response.body());
                }
                else pcSupport.firePropertyChange("connectionError", null, null);
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                pcSupport.firePropertyChange("connectionError", null, null);
            }
        });
    }

    public void getDoctorAppointmentsForDay(String userId, long date) {
        Call<List<ApiDoctorAppointment>> call = api.getDoctorAppointmentsPerDay(userId, date);
        call.enqueue(new Callback<List<ApiDoctorAppointment>>() {
            @Override
            public void onResponse(Call<List<ApiDoctorAppointment>> call, Response<List<ApiDoctorAppointment>> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotDoctorAppointmentsForDay", null, ListConverters.toDoctorAppointmentList(response.body()));
                }
                else pcSupport.firePropertyChange("gotDoctorAppointmentsForDayFail", null, null);
            }

            @Override
            public void onFailure(Call<List<ApiDoctorAppointment>> call, Throwable t) {
                pcSupport.firePropertyChange("gotDoctorAppointmentsForDayFail", null, null);
            }
        });
    }

    public void getDoctorAppointment(int id) {
        Call<ApiDoctorAppointment> call = api.getDoctorAppointment(id);
        call.enqueue(new Callback<ApiDoctorAppointment>() {
            @Override
            public void onResponse(Call<ApiDoctorAppointment> call, Response<ApiDoctorAppointment> response) {
                if (response.code() == 200) {
                    pcSupport.firePropertyChange("gotDoctorAppointment", null, response.body().getDoctorAppointment());
                }
                else pcSupport.firePropertyChange("gotDoctorAppointmentFail", null, null);
            }

            @Override
            public void onFailure(Call<ApiDoctorAppointment> call, Throwable t) {
                pcSupport.firePropertyChange("gotDoctorAppointmentFail", null, null);
            }
        });
    }
}
