package com.example.jjandroidchallenge.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.repository.Repository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private Repository mRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    public LiveData<List<Device>> getAllDevices(){
        return mRepository.getAllDevices();
    }
}
