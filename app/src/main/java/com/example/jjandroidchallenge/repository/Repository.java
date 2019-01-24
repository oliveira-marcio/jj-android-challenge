package com.example.jjandroidchallenge.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jjandroidchallenge.R;
import com.example.jjandroidchallenge.database.DeviceDao;
import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.network.DeviceDataService;
import com.example.jjandroidchallenge.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private final String LOG = Repository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static Repository sInstance;

    private final Context mContext;
    private final DeviceDao mDeviceDao;
    private final AppExecutors mExecutors;
    private final DeviceDataService mDataService;

    public Repository(Context context,
                      DeviceDao deviceDao,
                      AppExecutors executors,
                      DeviceDataService dataService) {
        mContext = context;
        mDeviceDao = deviceDao;
        mExecutors = executors;
        mDataService = dataService;
    }

    public synchronized static Repository getInstance(Context context,
                                                      DeviceDao deviceDao,
                                                      AppExecutors executors,
                                                      DeviceDataService dataService) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Repository(context, deviceDao, executors, dataService);
            }
        }
        return sInstance;
    }

    private void fetchAndSyncDevices() {
        mDataService.getDevices().enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, final Response<List<Device>> response) {
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDeviceDao.deleteAllDevices();
                        mDeviceDao.bulkInsert(response.body());
                    }
                });
                Toast.makeText(mContext, R.string.data_sync_success, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Toast.makeText(mContext, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<List<Device>> getAllDevices() {
        fetchAndSyncDevices();
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

        mDataService.postDevice(newDevice).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(LOG, "HTTP CODE: " + response.code());
                Device device = response.body();
                if (device != null) {
                    Log.i(LOG, "" + device.getId());
                    Log.i(LOG, device.getDevice());
                    Log.i(LOG, device.getOs());
                    Log.i(LOG, device.getManufacturer());
                    Log.i(LOG, "" + device.getIsCheckedOut());
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Toast.makeText(mContext, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
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

        mDataService.deleteDevice(device.getId()).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(LOG, "HTTP CODE: " + response.code());
                Device device = response.body();
                if (device != null) {
                    Log.i(LOG, "" + device.getId());
                    Log.i(LOG, device.getDevice());
                    Log.i(LOG, device.getOs());
                    Log.i(LOG, device.getManufacturer());
                    Log.i(LOG, "" + device.getIsCheckedOut());
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Toast.makeText(mContext, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
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

        mDataService.updateDevice(device.getId(), device).enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(LOG, "HTTP CODE: " + response.code());
                Device device = response.body();
                if (device != null) {
                    Log.i(LOG, "" + device.getId());
                    Log.i(LOG, device.getDevice());
                    Log.i(LOG, device.getOs());
                    Log.i(LOG, device.getManufacturer());
                    Log.i(LOG, "" + device.getIsCheckedOut());
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Toast.makeText(mContext, R.string.data_sync_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
