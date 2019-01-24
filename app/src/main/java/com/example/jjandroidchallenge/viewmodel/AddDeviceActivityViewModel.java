package com.example.jjandroidchallenge.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.jjandroidchallenge.repository.Repository;

public class AddDeviceActivityViewModel extends ViewModel {
    private final Repository mRepository;

    public AddDeviceActivityViewModel(Repository repository) {
        mRepository = repository;
    }

    public void addDevice(String device, String os, String manufacturer) {
        mRepository.addDevice(device, os, manufacturer);
    }
}
