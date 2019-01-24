package com.example.jjandroidchallenge.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.repository.Repository;

public class DetailsActivityViewModel extends ViewModel {
    private final Repository mRepository;
    private final LiveData<Device> mDevice;

    public DetailsActivityViewModel(Repository repository, long deviceId) {
        mRepository = repository;
        mDevice = mRepository.getDeviceById(deviceId);
    }

    public LiveData<Device> getDevice() {
        return mDevice;
    }

    public void toggleCheckStatus(boolean isCheckOut, String CheckOutBy, String CheckOutDate) {
        mRepository.toggleCheckedStatus(
                mDevice.getValue(),
                CheckOutBy,
                CheckOutDate,
                isCheckOut
        );
    }
}
