package com.example.jjandroidchallenge.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.repository.Repository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private final Repository mRepository;

    public MainActivityViewModel(Repository repository) {
        mRepository = repository;
    }

    public LiveData<List<Device>> getAllDevices(){
        return mRepository.getAllDevices();
    }
}
