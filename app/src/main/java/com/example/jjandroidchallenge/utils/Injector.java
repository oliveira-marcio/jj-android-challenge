package com.example.jjandroidchallenge.utils;

import android.content.Context;

import com.example.jjandroidchallenge.database.DeviceDatabase;
import com.example.jjandroidchallenge.network.DeviceDataClient;
import com.example.jjandroidchallenge.network.DeviceDataService;
import com.example.jjandroidchallenge.repository.Repository;
import com.example.jjandroidchallenge.viewmodel.AddDeviceViewModelFactory;
import com.example.jjandroidchallenge.viewmodel.DetailsViewModelFactory;
import com.example.jjandroidchallenge.viewmodel.MainViewModelFactory;

public class Injector {
    public static Repository provideRepository(Context context) {
        DeviceDatabase database = DeviceDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        DeviceDataService dataService = DeviceDataClient.getInstance();
        return Repository.getInstance(context.getApplicationContext(), database.deviceDao(), executors, dataService);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

    public static DetailsViewModelFactory provideDetailsViewModelFactory(Context context, long deviceId) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new DetailsViewModelFactory(repository, deviceId);
    }

    public static AddDeviceViewModelFactory provideAddDeviceViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new AddDeviceViewModelFactory(repository);
    }
}
