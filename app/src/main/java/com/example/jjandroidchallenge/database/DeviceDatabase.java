package com.example.jjandroidchallenge.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.jjandroidchallenge.models.Device;

@Database(entities = {Device.class}, version = 1, exportSchema = false)
public abstract class DeviceDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "devices";

    private static final Object LOCK = new Object();
    private static DeviceDatabase sInstance;

    public static DeviceDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        DeviceDatabase.class, DeviceDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract DeviceDao deviceDao();
}
