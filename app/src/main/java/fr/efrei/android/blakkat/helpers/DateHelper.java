package fr.efrei.android.blakkat.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    private static DateFormat frenchFormat;

    public static String format(Date d) {
        return DateHelper.format("%s", d);
    }

    public static String format(String s, Date d) {
        if(frenchFormat == null)
            DateHelper.initFormat();
        return String.format(s, DateHelper.frenchFormat.format(d));
    }

    public static String formatInternational(Date d) {
        if(frenchFormat == null)
            DateHelper.initFormat();
        return String.format("%tF", d);
    }

    private static void initFormat() {
        DateHelper.frenchFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.FRENCH);
    }
}
