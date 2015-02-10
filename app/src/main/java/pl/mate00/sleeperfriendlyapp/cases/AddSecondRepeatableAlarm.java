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

import static org.joda.time.DateTimeConstants.TUESDAY;

/**
 * Created by mamy on 30.01.15.
 */
public class AddSecondRepeatableAlarm {

    private static Timeline timeline = new TimelineConsole();
    private static Set<RepeatableAlarm> uiAlarms = new HashSet<RepeatableAlarm>();


    public static void main(String[] args) {
        DateTime current = new DateTime().withDayOfWeek(TUESDAY).withTime(6, 0, 0, 0);

        System.out.println(timeline);

        RepeatableAlarm firstUiAlarm = new RepeatableAlarm(Time.of(10, 0), new int[] {TUESDAY});
        RepeatableAlarm secondUiAlarm = new RepeatableAlarm(Time.of(8, 0), new int[] {1, 2, 3});

        addAlarmsToTimeline(firstUiAlarm.breakIntoPieces());
        updateUIListOfAlarms(firstUiAlarm);
        System.out.println("closest: " + getClosestAlarmTo(current));

        addAlarmsToTimeline(secondUiAlarm.breakIntoPieces());
        updateUIListOfAlarms(secondUiAlarm);
        System.out.println("closest: " + getClosestAlarmTo(current));

        System.out.println(timeline);
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

}
