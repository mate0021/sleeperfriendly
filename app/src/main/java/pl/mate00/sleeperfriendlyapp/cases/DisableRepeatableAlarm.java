package pl.mate00.sleeperfriendlyapp.cases;

import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

/**
 * Created by mamy on 30.01.15.
 */
public class DisableRepeatableAlarm {

    private static Timeline timeline = new Timeline();
    private static Set<RepeatableAlarm> uiAlarms = new HashSet<>();

    public static void main(String[] args) {
        DateTime current = new DateTime().withDayOfWeek(1).withTime(6, 0, 0, 0);
        System.out.println(timeline);
        System.out.println(uiAlarms);

        RepeatableAlarm alarm = new RepeatableAlarm(Time.from(7, 0), new int[] {1, 2, 3});
        addAlarmsToTimeline(alarm.breakIntoPieces());
        updateUIListOfAlarms(alarm);

        System.out.println(getClosestAlarmTo(current));

        disableRepeatableAlarm(alarm);
        System.out.println(timeline);
        System.out.println(uiAlarms);

        System.out.println(getClosestAlarmTo(current));
    }

    public static void addAlarmsToTimeline(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            System.out.println(alarm + " put to timeline.");
            timeline.addAlarm(alarm);
        }
    }

    public static void updateUIListOfAlarms(RepeatableAlarm alarm) {
        uiAlarms.add(alarm);
        System.out.println(uiAlarms);
    }

    public static Alarm getClosestAlarmTo(DateTime current) {
        return timeline.getClosestTo(current);
    }

    public static void disableRepeatableAlarm(RepeatableAlarm alarm) {
        alarm.setEnabled(false);
        for (Alarm a : alarm.breakIntoPieces()) {
            timeline.removeAlarm(a);
        }
    }
}
