package com.example.jjandroidchallenge.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeviceDataClient {

    private static final Object LOCK = new Object();
    private static DeviceDataService sInstance;
    private static String BASE_URL = "http://private-1cc0f-devicecheckout.apiary-mock.com/";

    public synchronized static DeviceDataService getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Retrofit
                        .Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(DeviceDataService.class);
            }
        }
        return sInstance;
    }
}
