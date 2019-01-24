package com.example.jjandroidchallenge.utils;

import android.content.Context;

import com.example.jjandroidchallenge.database.DeviceDatabase;
import com.example.jjandroidchallenge.repository.Repository;
import com.example.jjandroidchallenge.viewmodel.AddDeviceViewModelFactory;
import com.example.jjandroidchallenge.viewmodel.DetailsViewModelFactory;
import com.example.jjandroidchallenge.viewmodel.MainViewModelFactory;

public class Injector {
    public static Repository provideRepository(Context context) {
        DeviceDatabase database = DeviceDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return Repository.getInstance(database.deviceDao(), executors);
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
