package de.crysxd.mobilefitness.log;

/**
 * A interface for remote loggers
 */
public interface MfRemoteLogger {

    /**
     * Logs the string to the remote logger
     * @param tag the log's tag
     * @param message the log's message
     */
    void log(String tag, String message);

    /**
     * Logs the string to the remote logger
     * @param tag the log's tag
     * @param message the log's message
     * @param e the {@ilnk Throwable} to be logged
     */
    void log(String tag, String message, Throwable e);

}
