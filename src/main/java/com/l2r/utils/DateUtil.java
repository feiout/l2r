package com.l2r.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    public final static Date getDateAt0(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public final static Date getDateAt24(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Converts the given date to be at the end of the day specified.  That is,
     * changes the time portion of the given date to 23:59:59.999.  Since java.util.date
     * doesn't have milliseconds, it rolls the time to the next day when milliseconds
     * are set.  Therefore, we only set millis for java.sql.date
     * @param inDate the date to convert; can be null, in which case this method
     *          returns null.
     * @return a new Date object
     */
    public static Date convertToEndOfDay(Date inDate) {
        if(inDate != null){
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(inDate);
            gc.set(Calendar.HOUR_OF_DAY,23);
            gc.set(Calendar.MINUTE,59);
            gc.set(Calendar.SECOND,59);
            if(inDate instanceof java.sql.Date){
                gc.set(Calendar.MILLISECOND, 999);
            }
            return gc.getTime();
        }
        else { return null; }
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 根据天获取毫秒
     * @param days
     * @return
     */
    public static Long getMillisecondFromDays(Long days){
        return days * 24 * 60 * 60 * 1000;
    }

    public static Date formatDate(String dateStr){
        SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return myFmt1.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatDateByFormat(String dateStr, String format){
        SimpleDateFormat myFmt1=new SimpleDateFormat(format);
        try {
            return myFmt1.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        long x = 1500480000000L;
        Date date = new Date(x);
        System.out.printf(toDateString(date));
    }

    public static String toDateString(Date refDate) {
        SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
        return myFmt1.format(refDate);
    }

    public static String toDateTimeString(Date refDate) {
        SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt1.format(refDate);
    }

    public static String toDateString(Date refDate, String format) {
        SimpleDateFormat myFmt1=new SimpleDateFormat(format);
        return myFmt1.format(refDate);
    }

    public static Date getRefDateForHHMMSS(Date date, String dateS) {
        return DateUtil.formatDateByFormat(DateUtil.toDateString(date)+" "+dateS+":00:00", "yyyy-MM-dd HH:mm:ss");
    }


}
