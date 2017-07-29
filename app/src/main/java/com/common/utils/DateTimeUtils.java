package com.common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class DateTimeUtils {

    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;
//    private static final String TAG = makeLogTag(DateTimeUtils.class);
    private static final SimpleDateFormat[] ACCEPTED_TIMESTAMP_FORMATS = {
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US),
            new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z", Locale.US),
            new SimpleDateFormat("dd,MMM,yyyy HH:mm:ss aa", Locale.US),
            new SimpleDateFormat("dd MMM,yyyy HH:mm:ss aa", Locale.US),
            new SimpleDateFormat("dd-MMM-yyyy", Locale.US),
            new SimpleDateFormat("dd MMM, yyyy", Locale.US)
    };

    private static final SimpleDateFormat VALID_IFMODIFIEDSINCE_FORMAT =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);

    public static String DATE_FORMAT = "dd MMM, yyyy";
    public static String DATE_TIME_FORMAT = "dd MMM, yyyy HH:mm:ss aa";

    //TODO: All the APIs are needed to pass date values in this format
    public static String PHP_DATE_TIME_FORMAT = "yyyy-MM-dd";
    public static String PHP_DATE_TIME_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.US);

    public static Date parseTimestamp(String timestamp) {
        for (SimpleDateFormat format : ACCEPTED_TIMESTAMP_FORMATS) {
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                return format.parse(timestamp);
            } catch (ParseException ex) {
                continue;
            }
        }

        // All attempts to parse have failed
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(int year, int month, int day, boolean isAPIDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf;
        if (isAPIDate){
            sdf = new SimpleDateFormat("dd-MM-yyyy");
        }else {
            sdf = new SimpleDateFormat("MMM dd, yyyy");
        }
        return sdf.format(date);
    }

    public static long timestampToMillis(String timestamp, long defaultValue) {
        if (TextUtils.isEmpty(timestamp)) {
            return defaultValue;
        }
        Date d = parseTimestamp(timestamp);
        return d == null ? defaultValue : d.getTime();
    }

    /*
     * Converts ISO date string to UTC timezone equivalent.
     *
     * @param dateAndTime
     *            ISO formatted time string.
     */
    public static String getUtcTime(String dateAndTime) {
        Date d = parseDate(dateAndTime);

        String format = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        // Convert Local Time to UTC
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(d);
    }

    /*
     * Parses date string and return a {@link java.util.Date} object
     *
     * @return The ISO formatted date object
     */
    public static Date parseDate(String date) {

        if (date == null) {
            return null;
        }

        StringBuffer sbDate = new StringBuffer();
        sbDate.append(date);
        String newDate = null;
        Date dateDT = null;

        try {
            newDate = sbDate.substring(0, 19);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rDate = newDate.replace("T", " ");
        String nDate = rDate.replaceAll("-", "/");

        try {
            dateDT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).parse(nDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDT;
    }

    /***
     * Converts UTC time formatted as ISO to device local time.
     * <p/>
     * Sample usage
     * <p/>
     * <pre>
     *
     * {
     * 	SimpleDateFormat sdf = new SimpleDateFormat(&quot;yyyy-MM-dd'T'HH:mm:ss.SSS'Z'&quot;);
     * 	d = toLocalTime(&quot;2014-10-08T09:46:04.455Z&quot;, sdf);
     * }
     * </pre>
     *
     * @param utcDate
     * @param sdf
     * @throws Exception
     */
    public static Date toLocalTime(String utcDate, SimpleDateFormat sdf) throws Exception {

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date localDate = sdf.parse(utcDate);

        sdf.setTimeZone(TimeZone.getDefault());
        String dateFormateInUTC = sdf.format(localDate);

        return sdf.parse(dateFormateInUTC);
    }

    /**
     * Returns abbreviated (3 letters) day of the week.
     *
     * @param date ISO format date
     * @return The name of the day of the week
     */
    public static String getDayOfWeekAbbreviated(String date) {
        Date dateDT = parseDate(date);

        if (dateDT == null) {
            return null;
        }

        // Get current date
        Calendar c = Calendar.getInstance();
        c.setTime(dateDT);
        int day = c.get(Calendar.DAY_OF_WEEK);

        String dayStr = null;

        switch (day) {

            case Calendar.SUNDAY:
                dayStr = "Sun";
                break;

            case Calendar.MONDAY:
                dayStr = "Mon";
                break;

            case Calendar.TUESDAY:
                dayStr = "Tue";
                break;

            case Calendar.WEDNESDAY:
                dayStr = "Wed";
                break;

            case Calendar.THURSDAY:
                dayStr = "Thu";
                break;

            case Calendar.FRIDAY:
                dayStr = "Fri";
                break;

            case Calendar.SATURDAY:
                dayStr = "Sat";
                break;
        }

        return dayStr;
    }

    /***
     * Gets the name of the month from the given date.
     *
     * @param date ISO format date
     * @return Returns the name of the month
     ***/
    public static String getMonth(String date) {
        Date dateDT = parseDate(date);

        if (dateDT == null) {
            return null;
        }

        // Get current date
        Calendar c = Calendar.getInstance();
        c.setTime(dateDT);
        int day = c.get(Calendar.MONTH);

        String dayStr = null;

        switch (day) {

            case Calendar.JANUARY:
                dayStr = "January";
                break;

            case Calendar.FEBRUARY:
                dayStr = "February";
                break;

            case Calendar.MARCH:
                dayStr = "March";
                break;

            case Calendar.APRIL:
                dayStr = "April";
                break;

            case Calendar.MAY:
                dayStr = "May";
                break;

            case Calendar.JUNE:
                dayStr = "June";
                break;

            case Calendar.JULY:
                dayStr = "July";
                break;

            case Calendar.AUGUST:
                dayStr = "August";
                break;

            case Calendar.SEPTEMBER:
                dayStr = "September";
                break;

            case Calendar.OCTOBER:
                dayStr = "October";
                break;

            case Calendar.NOVEMBER:
                dayStr = "November";
                break;

            case Calendar.DECEMBER:
                dayStr = "December";
                break;
        }

        return dayStr;
    }

    /**
     * Gets abbreviated name of the month from the given date.
     *
     * @param date ISO format date
     * @return Returns the name of the month
     */
    public static String getMonthAbbreviated(String date) {
        Date dateDT = parseDate(date);

        if (dateDT == null) {
            return null;
        }

        // Get current date
        Calendar c = Calendar.getInstance();
        c.setTime(dateDT);
        int day = c.get(Calendar.MONTH);

        String dayStr = null;

        switch (day) {

            case Calendar.JANUARY:
                dayStr = "Jan";
                break;

            case Calendar.FEBRUARY:
                dayStr = "Feb";
                break;

            case Calendar.MARCH:
                dayStr = "Mar";
                break;

            case Calendar.APRIL:
                dayStr = "Apr";
                break;

            case Calendar.MAY:
                dayStr = "May";
                break;

            case Calendar.JUNE:
                dayStr = "Jun";
                break;

            case Calendar.JULY:
                dayStr = "Jul";
                break;

            case Calendar.AUGUST:
                dayStr = "Aug";
                break;

            case Calendar.SEPTEMBER:
                dayStr = "Sep";
                break;

            case Calendar.OCTOBER:
                dayStr = "Oct";
                break;

            case Calendar.NOVEMBER:
                dayStr = "Nov";
                break;

            case Calendar.DECEMBER:
                dayStr = "Dec";
                break;
        }

        return dayStr;
    }


    /**
     * Transforms Calendar to ISO 8601 string.
     * **
     */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    public static String getDateFromMillis(long milliseconds, String format) {
        String strDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                    Locale.US);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);

            strDate = dateFormat.format(new Date(calendar.getTimeInMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String getPreviousDate(String format, int days) {
        String currentDateandTime;
        if (days != 0) {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, days);
            Date daysBeforeDate = cal.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            currentDateandTime = sdf.format(daysBeforeDate);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            currentDateandTime = sdf.format(new Date());
        }
        return currentDateandTime;
    }

    public static String getFormatedDate(String date, String format) {
        Date myDate = parseTimestamp(date);
        SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.getDefault());
        return timeFormat.format(myDate);
    }


    /**
     * get date before given day
     *
     * @param daysBefore : No of day before
     * @param format     : format of date
     */
    public static String getDateBeforeDays(int daysBefore, String format) {
        long daysInMillis = ((long) (daysBefore) * (24 * 60 * 60 * 1000));

        long diff = System.currentTimeMillis() - daysInMillis;
        String strDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            strDate = dateFormat.format(new Date(diff));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    public static String parseChatDate(long time, String format) {

        String strDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            strDate = dateFormat.format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static Date parseDate(String date, String format) {

        if (date == null) {
            return null;
        }

        Date dateDT = null;

        try {
            dateDT = new SimpleDateFormat(format, Locale.getDefault()).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDT;
    }
}