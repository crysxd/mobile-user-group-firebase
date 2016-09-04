package de.crysxd.mobilefitness.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import de.crysxd.mobilefitness.R;
import de.crysxd.mobilefitness.dagger.MfComponentHolder;
import de.crysxd.mobilefitness.log.RemoteLog;

/**
 * A class representing a record
 */
public class MfRecord {

    /**
     * All data for this record
     */
    private Data mData = new Data();

    /**
     * A {@link MfDrawableResourceConverter} instance
     */
    @Inject
    MfDrawableResourceConverter mDrawableConverter;

    /**
     * A {@link MfDateConverter} instance
     */
    @Inject
    MfDateConverter mDateConverter;

    /**
     * Empty constructor used for Firebase
     */
    public MfRecord() {
        MfComponentHolder.i().inject(this);
    }

    /**
     * Default constructor
     *
     * @param id       the record's id
     * @param time     the creation time
     * @param exercise the exercise name
     * @param amount   the amount
     * @param icon     the icon
     * @param unit     the unit
     */
    public MfRecord(@NonNull UUID id, @NonNull Date time, @NonNull String exercise, double amount, @DrawableRes int icon, @NonNull MfUnit unit) {
        this();
        mData.time = mDateConverter.toString(time);
        mData.exercise = exercise;
        mData.amount = amount;
        mData.icon = mDrawableConverter.toString(icon);
        mData.unit = unit.toString();
        mData.id = id.toString();
    }

    /**
     * Sets all data. This is required for Firebase
     *
     * @param data all data
     */
    public void setData(Data data) {
        mData = data;
    }

    /**
     * Returns all data. This is required for Firebase
     *
     * @return all data
     */
    public Data getData() {
        return mData;
    }

    /**
     * Sets the creation time
     *
     * @param time the creation time
     */
    @Exclude
    public void setTime(@NonNull Date time) {
        mData.time = mDateConverter.toString(time);
    }

    /**
     * Returns the creation time
     *
     * @return the creation time
     */
    @Exclude
    public Date getTime() {
        try {
            return mDateConverter.toDate(mData.time);
        } catch (ParseException e) {
            RemoteLog.log(getClass().getSimpleName(), "Error while reading date", e);
            return new Date();
        }
    }

    /**
     * Sets the exercise
     *
     * @param exercise the exercise
     */
    @Exclude
    public void setExercise(@NonNull String exercise) {
        mData.exercise = exercise;
    }

    /**
     * Returns the exercise
     *
     * @return the exercise
     */
    @Exclude
    public String getExercise() {
        return mData.exercise;
    }

    /**
     * Sets the icon
     *
     * @param icon the icon resource
     */
    @Exclude
    public void setIcon(@DrawableRes int icon) {
        mData.icon = mDrawableConverter.toString(icon);
    }

    /**
     * Returns the icon
     *
     * @return the icon resource
     */
    @Exclude
    @DrawableRes
    public int getIconResource() {
        return mDrawableConverter.toResource(mData.icon, R.drawable.ic_launcher_big);
    }

    /**
     * Sets the amount
     *
     * @param amount the amount
     */
    @Exclude
    public void setAmount(double amount) {
        mData.amount = amount;
    }

    /**
     * Returns the amount
     *
     * @return the amount
     */
    @Exclude
    public double getAmount() {
        return mData.amount;
    }

    /**
     * Sets the unit
     *
     * @param unit the unit
     */
    @Exclude
    public void setUnit(MfUnit unit) {
        mData.unit = unit.toString();
    }

    /**
     * Returns the unit
     *
     * @return the unit
     */
    @Exclude
    public MfUnit getUnit() {
        return MfUnit.valueOf(mData.unit);
    }

    /**
     * Sets the id
     *
     * @param id the id
     */
    @Exclude
    public void getId(String id) {
        mData.id = id;
    }

    /**
     * Returns the id
     *
     * @return the id
     */
    @Exclude
    public UUID getId() {
        return UUID.fromString(mData.id);
    }

    /**
     * A public class containing all data. All fields must be public
     */
    public static class Data {

        /**
         * The id
         */
        public String id;

        /**
         * The exercise
         */
        public String exercise;

        /**
         * The amount
         */
        public double amount;

        /**
         * The unit
         */
        public String unit;

        /**
         * The icon
         */
        public String icon;

        /**
         * The time
         */
        public String time;
    }

    /**
     * A Builder for {@link MfRecord}
     */
    public static class Builder {

        /**
         * The id
         */
        private UUID mId = UUID.randomUUID();

        /**
         * The exercise
         */
        private String mExercise;

        /**
         * The amount
         */
        private double mAmount = Double.NaN;

        /**
         * The unit
         */
        private MfUnit mMfUnit = MfUnit.UNDEFINED;

        /**
         * The icon
         */
        @DrawableRes
        private int mIcon = 0;

        /**
         * The creation time, defaults to now
         */
        private Date mTime = new Date();

        /**
         * Sets the exercise
         *
         * @param exercise the exercise
         * @return this instance
         */
        public Builder setExercise(String exercise) {
            mExercise = exercise;
            return this;
        }

        /**
         * Sets the exercise
         *
         * @param amount the sexercise
         * @return this instance
         */
        public Builder setAmount(double amount) {
            mAmount = amount;
            return this;
        }

        /**
         * Sets the unit
         *
         * @param unit the unit
         * @return this instance
         */
        public Builder setUnit(MfUnit unit) {
            mMfUnit = unit;
            return this;
        }

        /**
         * Sets the icon
         *
         * @param icon the icon
         * @return this instance
         */
        public Builder setIcon(@DrawableRes int icon) {
            mIcon = icon;
            return this;
        }

        /**
         * Sets the time
         *
         * @param time the time
         * @return this instance
         */
        public Builder setTime(Date time) {
            mTime = time;
            return this;
        }

        /**
         * Sets the id
         *
         * @param id the id
         * @return this instance
         */
        public Builder setId(String id) {
            mId = UUID.fromString(id);
            return this;
        }

        /**
         * Builds the {@link MfRecord}
         *
         * @return the build instance
         */
        public MfRecord build() {
            if (mMfUnit == MfUnit.UNDEFINED || mAmount == Double.NaN || mExercise == null || mIcon == 0) {
                throw new IllegalArgumentException("Some required fields are not set");
            }

            return new MfRecord(mId, mTime, mExercise, mAmount, mIcon, mMfUnit);
        }
    }
}
