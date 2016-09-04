package de.crysxd.mobilefitness.data;

import android.content.Context;
import android.support.annotation.DrawableRes;

/**
 * A class which can be used to transform drawable resources to strings and back
 */
public class MfDrawableResourceConverter {

    /**
     * A {@link Context}
     */
    private Context mContext;

    /**
     * Creates a new instance
     *
     * @param con a {@link Context}
     */
    public MfDrawableResourceConverter(Context con) {
        mContext = con;
    }

    /**
     * Converts the given drawable resource to a String
     *
     * @param drawable the drawable resource
     * @return the string representation
     */
    public String toString(@DrawableRes int drawable) {
        return mContext.getResources().getResourceEntryName(drawable);

    }

    /**
     * Converts the given String to a drawable resource
     *
     * @param resourceName the resource name
     * @param fallback     the fallback returned if the operation fails
     * @return the drawable resource or the fallback
     */
    @DrawableRes
    public int toResource(String resourceName, @DrawableRes int fallback) {
        int id = mContext.getResources().getIdentifier(resourceName, "drawable", mContext.getPackageName());
        return id != 0 ? id : fallback;

    }
}
