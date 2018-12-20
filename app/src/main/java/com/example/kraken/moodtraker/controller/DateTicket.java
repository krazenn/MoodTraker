package com.example.kraken.moodtraker.controller;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

public class DateTicket {


    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    Date currentDate = calendar.getTime();
    DateFormat format = DateFormat.getDateInstance();

    public String format(Date date) {
        return format.format(date);
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Boolean compareDate(Date date1, Date date2) {
        boolean equalDate = false;
        String stringDate1 = format.format(date1);
        String stringDate2 = format.format(date2);
        if (stringDate1.equals(stringDate2)) {
            equalDate = true;
        }
        return equalDate;
    }
}
