package com.example.jjandroidchallenge.network;

import com.example.jjandroidchallenge.models.Device;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeviceDataService {

    @GET("devices")
    Call<List<Device>> getDevices();

    @POST("devices")
    Call<Device> postDevice(@Body Device device);

    @POST("devices/{id}")
    Call<Device> updateDevice(@Path("id") long id, @Body Device device);

    @DELETE("devices/{id}")
    Call<Device> deleteDevice(@Path("id") long id);
}
