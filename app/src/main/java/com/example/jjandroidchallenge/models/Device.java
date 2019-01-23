package com.example.jjandroidchallenge.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device implements Parcelable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("os")
    @Expose
    private String os;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("lastCheckedOutDate")
    @Expose
    private String lastCheckedOutDate;
    @SerializedName("lastCheckedOutBy")
    @Expose
    private String lastCheckedOutBy;
    @SerializedName("isCheckedOut")
    @Expose
    private Boolean isCheckedOut;

    public final static Parcelable.Creator<Device> CREATOR = new Creator<Device>() {
        @SuppressWarnings({
                "unchecked"
        })
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return (new Device[size]);
        }
    };

    protected Device(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.device = ((String) in.readValue((String.class.getClassLoader())));
        this.os = ((String) in.readValue((String.class.getClassLoader())));
        this.manufacturer = ((String) in.readValue((String.class.getClassLoader())));
        this.lastCheckedOutDate = ((String) in.readValue((String.class.getClassLoader())));
        this.lastCheckedOutBy = ((String) in.readValue((String.class.getClassLoader())));
        this.isCheckedOut = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Device() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLastCheckedOutDate() {
        return lastCheckedOutDate;
    }

    public void setLastCheckedOutDate(String lastCheckedOutDate) {
        this.lastCheckedOutDate = lastCheckedOutDate;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public Boolean getIsCheckedOut() {
        return isCheckedOut;
    }

    public void setIsCheckedOut(Boolean isCheckedOut) {
        this.isCheckedOut = isCheckedOut;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(device);
        dest.writeValue(os);
        dest.writeValue(manufacturer);
        dest.writeValue(lastCheckedOutDate);
        dest.writeValue(lastCheckedOutBy);
        dest.writeValue(isCheckedOut);
    }

    public int describeContents() {
        return 0;
    }
}