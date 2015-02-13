package pl.mate00.sleeperfriendlyapp.cases;

import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.TimelineConsole;

/**
 * Created by mamy on 13.02.15.
 */
public class AddSecondAlarmWithNoDays {

    private static Timeline timeline = new TimelineConsole();
    private static Set<RepeatableAlarm> uiAlarms = new HashSet<RepeatableAlarm>();

    public static void main(String[] args) {
        RepeatableAlarm a1 = new RepeatableAlarm(Time.of(8, 14), new int[] {1, 2});
        addAlarmsToTimeline(a1.breakIntoPieces());
        updateUIListOfAlarms(a1);

        System.out.println(timeline);
        System.out.println(uiAlarms);

        DateTime current = new DateTime().withDayOfWeek(1).withHourOfDay(10);

        RepeatableAlarm a2 = new RepeatableAlarm(Time.of(9, 0), current);
        addAlarmsToTimeline(a2.breakIntoPieces());
        updateUIListOfAlarms(a2);

        System.out.println();
        System.out.println(timeline);
        System.out.println(uiAlarms);
    }

    public static void addAlarmsToTimeline(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            timeline.addAlarm(alarm);
        }
    }

    public static void updateUIListOfAlarms(RepeatableAlarm alarm) {
        uiAlarms.add(alarm);
    }

    public static Alarm getClosestAlarmTo(DateTime current) {
        return timeline.getClosestTo(current);
    }
}
