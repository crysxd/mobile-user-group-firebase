package de.crysxd.mobilefitness.data;

import java.util.Calendar;
import java.util.Date;

/**
 * A range between two dates
 */
public class MfDateRange {

    /**
     * The start date
     */
    private Date mFrom;

    /**
     * The end date
     */
    private Date mTo;

    /**
     * The description for the range, e.g. "Today"
     */
    private String mDescription;

    /**
     * The instance id for today
     */
    public final static int TODAY = 0;

    /**
     * The instance id for this week
     */
    public final static int THIS_WEEK = 1;

    /**
     * The instance id for this month
     */
    public final static int THIS_MONTH = 2;

    /**
     * The instance id for older dates
     */
    public final static int BEFORE = 3;

    /**
     * Returns a preset instance
     * @param instanceId the id of the instance
     * @return the instance
     */
    public static MfDateRange getInstance(int instanceId) {
        // Create calendar at set to 12 AM
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date fromToday = c.getTime();

        // Set to start of week
        c.add(Calendar.DAY_OF_MONTH, -7);
        Date fromThisWeek = c.getTime();

        // Set to start of month
        c.set(Calendar.DAY_OF_MONTH, -23);
        Date fromThisMonth = c.getTime();

        switch (instanceId) {
            case TODAY:
                return new MfDateRange(fromToday, new Date(Long.MAX_VALUE), "Today");
            case THIS_WEEK:
                return new MfDateRange(fromThisWeek, fromToday, "This week");
            case THIS_MONTH:
                return new MfDateRange(fromThisMonth, fromThisWeek, "This month");
            default:
                return new MfDateRange(new Date(0), fromThisMonth, "Before");

        }
    }

    /**
     * Creates a new instance
     *
     * @param from the start date
     * @param to   the end date
     */
    public MfDateRange(Date from, Date to, String description) {
        mFrom = from;
        mTo = to;
        mDescription = description;
    }

    /**
     * Checks whether the given date is in this range
     *
     * @param d the date to check
     * @return true if in range, false if not
     */
    public boolean isInRange(Date d) {
        return mFrom.before(d) && (d.before(mTo) || d.getTime() == mTo.getTime());
    }

    /**
     * Returns the description for the date range, e.g. "Today"
     * @return the description
     */
    public String getDescription() {
        return mDescription;
    }
}
