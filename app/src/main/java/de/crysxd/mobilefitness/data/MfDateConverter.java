package de.crysxd.mobilefitness.data;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A class which can be used to convert {@link Date} objects to ISO8601 and ISO8601 strings to
 * {@link Date} objects
 */
@SuppressLint("SimpleDateFormat")
public class MfDateConverter {

    public Date toDate(@NonNull String d) throws ParseException {
        // Remove the : in the timezone for SimpleDateFormatter to be able to parse
        String fixedIso = d;
        StringBuilder b = new StringBuilder(fixedIso);
        int lastDoublePoint = b.lastIndexOf(":");


        // If double point was found, remove it
        if(lastDoublePoint >= 0) {
            // Remove the :
            fixedIso = b.replace(lastDoublePoint, lastDoublePoint + 1, "").toString();
        }

        // Parse and return
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return df1.parse(fixedIso);

    }

    /**
     * Transforms the given date in a ISO8601 conform string.
     * @param d the date which should be transformed
     * @return the generated string
     */
    public String toString(Date d) {
        return toString(d, null);

    }

    /**
     * Transforms the given date in a ISO8601 conform string.
     *
     * @param d  the date which should be transformed
     * @param tz the {@link TimeZone} in which the string should be represented
     * @return the generated string
     */
    public String toString(Date d, @Nullable TimeZone tz) {
        // Set d if date is null to prevent nullpointer
        if (d == null) {
            d = new Date();
        }

        // Create formatted
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        if (tz != null) {
            df.setTimeZone(tz);

        }

        // Format
        String iso = df.format(d);

        // Insert : in timezone to be conform with ISO8601
        StringBuilder b = new StringBuilder(iso);
        b.replace(b.length() - 2, b.length() - 2, ":");

        // Return the result
        return b.toString();
    }
}
