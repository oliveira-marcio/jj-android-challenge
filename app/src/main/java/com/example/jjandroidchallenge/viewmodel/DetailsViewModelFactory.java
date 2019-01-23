package com.example.jjandroidchallenge.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.jjandroidchallenge.repository.Repository;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Repository mRepository;
    private final long mDeviceId;

    public DetailsViewModelFactory(Repository repository, long deviceId) {
        mRepository = repository;
        mDeviceId = deviceId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsActivityViewModel(mRepository, mDeviceId);
    }
}
