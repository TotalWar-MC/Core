package com.steffbeard.totalwar.modules.totalbot.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtils {

    public static String getCurrentSystemTime() {
        DateFormat dateFormat = new SimpleDateFormat("[dd.MM.yyyy - HH:mm:ss]");
        Date date = new Date();

        return dateFormat.format(date);
    }
}