package com.example.assignment.apiConnection;

import com.example.assignment.Model.Appointment;
import com.example.assignment.Model.Doctor;
import com.example.assignment.Shared.DoctorAppointmentTimes;
import com.example.assignment.apiConnection.ApiModel.ApiDoctorAppointment;
import com.example.assignment.apiConnection.ApiModel.ApiPatient;
import com.example.assignment.apiConnection.ApiModel.ApiPatientAppointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    @GET("patients/{patientId}")
    Call<ApiPatient> getPatient(@Path("patientId") String patientId);

    @POST("patients")
    Call<Void> postPatient(@Body ApiPatient patient);

    @PUT("patients")
    Call<Void> putPatient(@Body ApiPatient patient);

    @GET("doctors/{specialization}/{country}/{city}")
    Call<List<Doctor>> getDoctors(@Path("specialization") String specialization, @Path("country") String country, @Path("city") String city);

    @GET("doctors/{doctorId}")
    Call<Doctor> getDoctor(@Path("doctorId") String doctorId);

    @GET("patientappointments/{appointmentId}")
    Call<ApiPatientAppointment> getPatientAppointment(@Path("appointmentId") int id);

    @GET("patientappointments/{patientId}/upcomingAppointments/{date}")
    Call<List<ApiPatientAppointment>> getPatientUpcomingAppointments(@Path("patientId") String patientId, @Path("date") long date);

    @GET("patientappointments/{patientId}/pastAppointments/{date}")
    Call<List<ApiPatientAppointment>> getPatientPastAppointments(@Path("patientId") String id, @Path("date") long date);

    @POST("appointments")
    Call<Appointment> postAppointment(@Body Appointment appointment);

    @PUT("appointments")
    Call<Appointment> putAppointment(@Body Appointment appointment);

    @GET("doctorappointmenttimes/{doctorId}/availableTimesFor/{date}")
    Call<List<Long>> getDoctorAppointmentTimes(@Path("doctorId") String doctorId, @Path("date") long date);

    @GET("countries")
    Call<List<String>> getCountries();

    @GET("countries/{country}")
    Call<List<String>> getRegions(@Path("country") String country);

    @GET("countries/{country}/{region}")
    Call<List<String>> getCities(@Path("country") String country, @Path("region") String region);

    @POST("doctors")
    Call<Void> postDoctor(@Body Doctor doctor);

    @POST("doctorappointmenttimes")
    Call<Void> postDAT(@Body DoctorAppointmentTimes dat);

    @GET("specializations")
    Call<List<String>> getSpecializations();

    @GET("DoctorAppointments/{doctorId}/day/{date}")
    Call<List<ApiDoctorAppointment>> getDoctorAppointmentsPerDay(@Path("doctorId") String doctorId, @Path("date") long date);

    @GET("doctorappointments/{appointmentId}")
    Call<ApiDoctorAppointment> getDoctorAppointment(@Path("appointmentId") int id);
}
