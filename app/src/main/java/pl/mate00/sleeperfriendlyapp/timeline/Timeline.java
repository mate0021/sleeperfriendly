package pl.mate00.sleeperfriendlyapp.timeline;

import com.google.common.base.Optional;

import org.joda.time.DateTime;

import java.util.Set;

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
     * is empty, return empty Optional.
     *
     * @param currentTime
     * @return
     */
    Optional<Alarm> getClosestTo(DateTime currentTime);

    /**
     * Removes alarm from a timeline.
     * @param alarm
     */
    void removeAlarm(Alarm alarm);

    /**
     * Returns a collection containing all defined alarms.
     * @return
     */
    Set<Alarm> getAllAlarms();

    int getNumberOfAlarms();
}
