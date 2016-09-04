package de.crysxd.mobilefitness.log;

import com.google.firebase.crash.FirebaseCrash;

/**
 * A {@link MfRemoteLogger} which logs to Firebase
 */
public class MfFirebaseRemoteLogger implements MfRemoteLogger {

    @Override
    public void log(String tag, String message) {
        if (FirebaseCrash.isSingletonInitialized()) {
            FirebaseCrash.log("[" + tag + "] " + message);
        }
    }

    @Override
    public void log(String tag, String message, Throwable e) {
        if (FirebaseCrash.isSingletonInitialized()) {
            this.log(tag, message);
            FirebaseCrash.report(e);
        }
    }
}
