package pl.mate00.sleeperfriendlyapp.cases;

import org.joda.time.DateTime;

import java.util.List;
import java.util.TimerTask;

import static org.joda.time.DateTimeConstants.*;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

/**
 * Created by mamy on 27.01.15.
 */
public class AddAlarmCase {

    private static Timeline timeline = new Timeline();
    private static List<Alarm> uiAlarms = new
    public static void main(String[] arg) {
        Alarm[] alarmsFromUI = getAlarmsFromUi();
        addAlarmsToTimeline(alarmsFromUI);

        System.out.println(timeline);

        Alarm closest = getClosestAlarm();
        System.out.println(closest);

        addClosestToAlarmManager(closest);
        updateUIListOfAlarms();
    }

    private static void updateUIListOfAlarms(Alarm[] alarms) {

        for (Alarm alarm : alarms) {
            System.out.println();
        }
    }

    private static void addClosestToAlarmManager(Alarm closest) {
        // if different than current closest
    }

    private static Alarm getClosestAlarm() {
        DateTime current = new DateTime().withDate(2015, 1, 28).withTime(10, 0, 0, 0);
        return timeline.getClosestTo(current);
    }

    private static void addAlarmsToTimeline(Alarm[] alarms) {
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
}