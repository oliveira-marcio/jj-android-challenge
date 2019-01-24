package com.example.jjandroidchallenge.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.repository.Repository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private final Repository mRepository;
    private LiveData<List<Device>> mDevices;

    public MainActivityViewModel(Repository repository) {
        mRepository = repository;
        mDevices = mRepository.getAllDevices();
    }

    public LiveData<List<Device>> getAllDevices() {
        return mDevices;
    }

    public void refreshDevices(){
        mDevices = mRepository.getAllDevices();
    }

    public void removeDevice(Device device) {
        mRepository.removeDeviceById(device);
    }
}
