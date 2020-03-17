package fr.efrei.android.blakkat.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class eases the interaction with {@link Date} objects
 * by allowing a quick and easy conversion to a {@link String}
 */
public class DateHelper {
    /**
     * French (DD-MM-YYYY) date format representation
     */
    private static DateFormat frenchFormat;

    /**
     * Formats the given {@link Date} to a french (DD-MM-YYYY) {@link String} representation
     * @param d {@link Date} to format
     * @return {@link String} in DD-MM-YYYY
     */
    public static String format(Date d) {
        return DateHelper.format("%s", d);
    }

    /**
     * Formats the given {@link Date} to a french (DD-MM-YYYY) {@link String} representation
     * and includes it into the given {@link String}, which should be ready to be formatted 
     * in accordance with {@link String#format} (%s), as seen in {@link DateHelper#format(Date)}
     * @param s {@link String} in which the {@link Date} should be included
     * @param d {@link Date} to format
     * @return given {@link String} in DD-MM-YYYY
     */
    public static String format(String s, Date d) {
        if(frenchFormat == null)
            DateHelper.initFormat();
        return String.format(s, DateHelper.frenchFormat.format(d));
    }

    /**
     * Formats the given {@link Date} to the ISO format (YYYY-MM-DD)
     * @param d {@link Date} to format
     * @return {@link String} in YYYY-MM-DD
     */
    public static String formatInternational(Date d) {
        if(frenchFormat == null)
            DateHelper.initFormat();
        return String.format("%tF", d);
    }

    /**
     * Initializes the french locale used in formatting
     */
    private static void initFormat() {
        DateHelper.frenchFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.FRENCH);
    }
}
