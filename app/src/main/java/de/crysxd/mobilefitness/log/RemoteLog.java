package de.crysxd.mobilefitness.log;

import android.support.annotation.NonNull;

/**
 * A static holder for the globally used {@link MfRemoteLogger}
 */
public class RemoteLog {

    /**
     * The currently used {@link MfRemoteLogger}
     */
    private static MfRemoteLogger sLogger = new MfDebugRemoteLogger();

    /**
     * Sets the {@link MfRemoteLogger} to be used
     * @param logger the {@link MfRemoteLogger}
     */
    public static void inject(@NonNull MfRemoteLogger logger) {
        sLogger = logger;
    }

    /**
     * Logs the string to the remote logger
     * @param tag the log's tag
     * @param message the log's message
     */
    public static void log(String tag, String message) {
        sLogger.log(tag, message);
    }

    /**
     * Logs the string to the remote logger
     * @param tag the log's tag
     * @param message the log's message
     * @param t the {@link Throwable} to be logged
     */
    public static void log(String tag, String message, Throwable t) {
        sLogger.log(tag, message, t);
    }
}
