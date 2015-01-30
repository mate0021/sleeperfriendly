package pl.mate00.sleeperfriendlyapp.timeline;

import org.joda.time.DateTime;

/**
 * Created by mamy on 30.01.15.
 */
public interface Timeline {

    boolean isEmpty();

    /**
     * Puts alarm on a timeline. Alarms has to be unique.
     * @param alarm
     */
    void addAlarm(Alarm alarm);

    /**
     * Returns alarm from timeline that is closest to, but greater than a given argument and is
     * enabled. If no such alarm is found, the first alarm from timeline is returned. If timeline
     * is empty, return null.
     *
     * @param currentTime
     * @return
     */
    Alarm getClosestTo(DateTime currentTime);

    /**
     * Removes alarm from a timeline.
     * @param alarm
     */
    void removeAlarm(Alarm alarm);

    int getNumberOfAlarms();
}
