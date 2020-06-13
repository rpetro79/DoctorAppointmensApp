package com.example.assignment.apiConnection.ApiModel;

import com.example.assignment.apiConnection.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://doctorappointments.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static API api = retrofit.create(API.class);

    public static API getApi() {
        return api;
    }

}
