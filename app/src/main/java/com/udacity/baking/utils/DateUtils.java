package com.udacity.baking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabiano.alvarenga on 10/22/17.
 */

public class DateUtils {

    private static SimpleDateFormat format;

    public static String dateToString(Date date) {
        format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public static Date stringToDate(String date) {
        format = new SimpleDateFormat("dd/MM/yyyy");
        Date result = null;
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getYearDate(Date date) {
        String dateString = dateToString(date);
        return dateString.substring(6,10);
    }

}
