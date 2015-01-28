package pl.mate00.sleeperfriendlyapp.cases;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.TreeSet;

import static org.joda.time.DateTimeConstants.*;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

/**
 * Created by mamy on 27.01.15.
 */
public class AddAlarmCase {

    private static Timeline timeline = new Timeline();
    private static Set<RepeatableAlarm> uiAlarms = new HashSet<>();

    public static void main(String[] arg) {
        System.out.println(timeline);

        RepeatableAlarm repeatableAlarm = getUiAlarm();
        List<Alarm> alarms = repeatableAlarm.breakIntoPieces();
        addAlarmsToTimeline(alarms);

        System.out.println(timeline);

        Alarm closest = getClosestAlarm();

        System.out.println(closest);

        addClosestToAlarmManager(closest);

        updateUIListOfAlarms(repeatableAlarm);

    }

    private static void updateUIListOfAlarms(RepeatableAlarm alarm) {
        uiAlarms.add(alarm);
    }

    private static void addClosestToAlarmManager(Alarm closest) {
        // if different than current closest
    }

    private static Alarm getClosestAlarm() {
        DateTime current = new DateTime().withDate(2015, 1, 28).withTime(10, 0, 0, 0);
        return timeline.getClosestTo(current);
    }

    private static void addAlarmsToTimeline(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            System.out.println(alarm + " put to timeline.");
            timeline.addAlarm(alarm);
        }
    }
    
    private static Alarm getAlarmFromUI() {
        return new Alarm(Time.from(8, 15), MONDAY);
    }

    // on UI, user sets time and picks days of week. For each dow, separate Alarm object
    // is created.
    private static Alarm[] getAlarmsFromUi() {
        Alarm[] result = new Alarm[] {
                new Alarm(Time.from(8, 15), MONDAY),
                new Alarm(Time.from(8, 15), TUESDAY),
                new Alarm(Time.from(8, 15), WEDNESDAY),
                new Alarm(Time.from(8, 15), THURSDAY),
                new Alarm(Time.from(8, 15), FRIDAY)
        };

        return result;
    }

    private static RepeatableAlarm getUiAlarm() {
        RepeatableAlarm result = new RepeatableAlarm(Time.from(8, 15), new int[] {1, 2, 3, 4, 5});

        return result;
    }
}


class RepeatableAlarm {
    private Time time;
    private int[] days;
    private boolean isEnabled;

    public RepeatableAlarm(Time time, int[] days) {
        this.time = time;
        this.days = days;
        isEnabled = true;
    }

    public List<Alarm> breakIntoPieces() {
        List<Alarm> result = new ArrayList<>(days.length);
        for (int day : days) {
            result.add(new Alarm(time, day));
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepeatableAlarm that = (RepeatableAlarm) o;

        if (!time.equals(that.time)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}