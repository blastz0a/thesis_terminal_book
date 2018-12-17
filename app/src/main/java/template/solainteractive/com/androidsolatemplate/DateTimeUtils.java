package template.solainteractive.com.androidsolatemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by BillySaputra on 28-Aug-17.
 */

public class DateTimeUtils {
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
    public static SimpleDateFormat dateFormatterIndonesia = new SimpleDateFormat(Constants.DATE_FORMAT, new Locale("id","IDN"));
    public static SimpleDateFormat dateFormatterDayIndo = new SimpleDateFormat("EEEE",new Locale("id","IDN"));
    public static SimpleDateFormat dateFormatterMonthIndo = new SimpleDateFormat("MMMM",new Locale("id","IDN"));
    public static SimpleDateFormat dateFormatterYearIndo = new SimpleDateFormat("yyyy",new Locale("id","IDN"));


    public static Date convertStringToDate(String dateString){
        Date dateTime = null;
        try {
            dateTime = dateFormatterIndonesia.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateTime;
    }
    public static String convertDateToString(Date dateTime) {
        String dateString = null;
        try {
            dateString = dateFormatterIndonesia.format(dateTime);
        }catch (Exception e){
            e.printStackTrace();
        }

        return dateString;
    }
    public static String getCurrentDate () {
        Calendar calendar = Calendar.getInstance();
        String date = convertDateToString(calendar.getTime());
        return date;
    }
    public static String setDateToDay (Date mDate) {
        String day = null;
        try {
            day = dateFormatterDayIndo.format(mDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return day;
    }

    public static String setDateToMonth(Date mDate){
        String month = null;
        try {
            month = dateFormatterMonthIndo.format(mDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return month;
    }
    public static String setDateToYear(Date mDate){
        String month = null;
        try {
            month = dateFormatterYearIndo.format(mDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return month;
    }
}
