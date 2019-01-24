package com.example.jjandroidchallenge.repository;

import android.arch.lifecycle.LiveData;

import com.example.jjandroidchallenge.database.DeviceDao;
import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.utils.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static final Object LOCK = new Object();
    private static Repository sInstance;
    private final DeviceDao mDeviceDao;
    private final AppExecutors mExecutors;

    public Repository(DeviceDao deviceDao,
                      AppExecutors executors) {
        mDeviceDao = deviceDao;
        mExecutors = executors;

        final List<Device> devices = getFakeDevices();
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDeviceDao.bulkInsert(devices);
            }
        });
    }

    public synchronized static Repository getInstance(DeviceDao deviceDao,
                                                      AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Repository(deviceDao, executors);
            }
        }
        return sInstance;
    }

    public LiveData<List<Device>> getAllDevices() {
        return mDeviceDao.getAllDevices();
    }

    public LiveData<Device> getDeviceById(long id) {
        return mDeviceDao.getDevice(id);
    }

    public void addDevice(String device, String os, String manufacturer) {
        final Device newDevice = new Device(device, os, manufacturer);
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDeviceDao.insertDevice(newDevice);
            }
        });
    }

    public void removeDeviceById(final Device device) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDeviceDao.deleteDevice(device);
            }
        });
    }

    public void toggleCheckedStatus(final Device device, String checkOutBy, String checkOutDate, boolean isCheckOut) {
        device.setIsCheckedOut(isCheckOut);
        device.setLastCheckedOutBy(checkOutBy);
        device.setLastCheckedOutDate(checkOutDate);

        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDeviceDao.updateDevice(device);
            }
        });
    }

    private List<Device> getFakeDevices() {
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
