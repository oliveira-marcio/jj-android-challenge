package com.example.jjandroidchallenge.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.jjandroidchallenge.models.Device;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static final Object LOCK = new Object();
    private static Repository sInstance;

    // TODO: Remove after real database implementation
    private MutableLiveData<List<Device>> mDevices = new MutableLiveData<>();
    private MutableLiveData<Device> mSelectedDevice = new MutableLiveData<>();

    public Repository() {
        mDevices.postValue(createFakeDevices());
    }

    public synchronized static Repository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Repository();
            }
        }
        return sInstance;
    }

    public MutableLiveData<List<Device>> getAllDevices() {
        return mDevices;
    }

    public LiveData<Device> getDeviceById(long id) {
        for (Device device : mDevices.getValue()) {
            if (device.getId() == id) {
                mSelectedDevice.setValue(device);
                break;
            }
        }
        return mSelectedDevice;
    }

    public void toggleCheckedStatus(long id, boolean newStatus) {
        List<Device> devices = mDevices.getValue();
        for (Device device : devices) {
            if (device.getId() == id) {
                device.setIsCheckedOut(newStatus);
                mSelectedDevice.setValue(device);
                mDevices.setValue(devices);
                break;
            }
        }
    }

    private List<Device> createFakeDevices() {
        String devicesJSON = "[\n" +
                "  {\n" +
                "    \"id\": 0,\n" +
                "    \"device\": \"iPhone 4\",\n" +
                "    \"os\": \"iOS 8.3\",\n" +
                "    \"manufacturer\": \"Apple\",\n" +
                "    \"lastCheckedOutDate\": \"2016-03-26T13:53:00-05:00\",\n" +
                "    \"lastCheckedOutBy\": \"Matt Smith\",\n" +
                "    \"isCheckedOut\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"device\": \"iPhone 5\",\n" +
                "    \"os\": \"iOS 9.1\",\n" +
                "    \"manufacturer\": \"Apple\",\n" +
                "    \"isCheckedOut\": false\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"device\": \"iPhone 5S\",\n" +
                "    \"os\": \"iOS 9.3\",\n" +
                "    \"manufacturer\": \"Apple\",\n" +
                "    \"lastCheckedOutDate\": \"2016-03-21T10:33:00-05:00\",\n" +
                "    \"lastCheckedOutBy\": \"David Richards\",\n" +
                "    \"isCheckedOut\": true\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"device\": \"Moto G\",\n" +
                "    \"os\": \"Android 4.3\",\n" +
                "    \"manufacturer\": \"Motorola\",\n" +
                "    \"lastCheckedOutDate\": \"2016-02-21T09:10:00-05:00\",\n" +
                "    \"lastCheckedOutBy\": \"Chris Evans\",\n" +
                "    \"isCheckedOut\": false\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 4,\n" +
                "    \"device\": \"iPhone 6S\",\n" +
                "    \"os\": \"iOS 9.3\",\n" +
                "    \"manufacturer\": \"Apple\",\n" +
                "    \"isCheckedOut\": false\n" +
                "  }\n" +
                "]";

        Type listType = new TypeToken<ArrayList<Device>>() {
        }.getType();
        return new Gson().fromJson(devicesJSON, listType);
    }
}
