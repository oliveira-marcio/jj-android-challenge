package com.example.jjandroidchallenge.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    private Utils(){

    }

    public static String dateToTMZ(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(date);
    }

    public static String TMZToDate (String date){
        SimpleDateFormat tmzFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
            return dateFormat.format(tmzFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}
