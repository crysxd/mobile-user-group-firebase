package de.crysxd.mobilefitness.log;

import android.util.Log;

/**
 * A {@link MfRemoteLogger} for debugging which only logs to Logcat
 */
public class MfDebugRemoteLogger implements MfRemoteLogger {

    @Override
    public void log(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void log(String tag, String message, Throwable e) {
        Log.e(tag, message, e);
    }
}
