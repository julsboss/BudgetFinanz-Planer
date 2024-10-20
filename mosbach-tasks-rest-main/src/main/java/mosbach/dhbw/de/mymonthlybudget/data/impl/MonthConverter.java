package mosbach.dhbw.de.mymonthlybudget.data.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

public class MonthConverter {
    public static int monthNameToNumber(String monthName) {
        try {
            Date date = new SimpleDateFormat("MMM", Locale.GERMAN).parse(monthName);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH) + 1; // Adding 1 to match human-readable format
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid month name: " + monthName, e);
        }
    }
}
