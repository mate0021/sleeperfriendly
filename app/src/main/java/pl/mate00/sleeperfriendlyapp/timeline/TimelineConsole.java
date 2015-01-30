package pl.mate00.sleeperfriendlyapp.timeline;

import org.joda.time.DateTime;

import java.util.Set;
import java.util.TreeSet;

/**
 * Class representing a sorted list of alarms.
 */
public class TimelineConsole implements Timeline {

    private Set<Alarm> line = new TreeSet<Alarm>();

    /**
     * Checks if there are no alarms on list.
     * @return
     */
    public boolean isEmpty() {
        return line.isEmpty();
    }

    /**
     * Puts alarm on a timeline. Alarms has to be unique.
     * @param alarm
     */
    public void addAlarm(Alarm alarm) {
        line.add(alarm);
    }

    /**
     * Returns alarm from timeline that is closest to, but greater than a given argument and is
     * enabled. If no such alarm is found, the first alarm from timeline is returned. If timeline
     * is empty, return null.
     *
     * @param currentTime
     * @return
     */
    public Alarm getClosestTo(DateTime currentTime) {
        return getFirstGreaterThan(currentTime);
    }

    public int getNumberOfAlarms() {
        return line.size();
    }

    private Alarm getFirstGreaterThan(DateTime time) {
        Alarm result = getFirstEnabledAlarm();

        for (Alarm a : line) {
            if (a.isLaterThan(time) && a.isEnabled()) {
                result = a;
                break;
            }
        }
        return result;
    }

    private Alarm getFirstEnabledAlarm() {
        Alarm result = null;
        for (Alarm alarm : line) {
            if (alarm.isEnabled()) {
                result = alarm;
                break;
            }
        }

        return result;
    }

    /**
     * Removes alarm from a timeline.
     * @param alarm
     */
    public void removeAlarm(Alarm alarm) {
        line.remove(alarm);
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "line=" + line +
                '}';
    }
}
