package com.example.jjandroidchallenge.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jjandroidchallenge.models.Device;

import java.util.List;

@Dao
public interface DeviceDao {
    @Query("SELECT * FROM devices")
    LiveData<List<Device>> getAllDevices();

    @Query("SELECT * FROM devices WHERE id = :id")
    LiveData<Device> getDevice(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<Device> devices);

    @Insert
    long insertDevice(Device device);

    @Delete
    int deleteDevice(Device device);

    @Query("DELETE FROM devices")
    void deleteAllDevices();

    @Update
    int updateDevice(Device device);
}
