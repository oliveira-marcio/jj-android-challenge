package com.example.jjandroidchallenge.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.jjandroidchallenge.repository.Repository;

public class AddDeviceViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Repository mRepository;

    public AddDeviceViewModelFactory(Repository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddDeviceActivityViewModel(mRepository);
    }
}
