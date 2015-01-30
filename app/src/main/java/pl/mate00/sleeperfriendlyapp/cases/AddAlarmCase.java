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
 * Created by mamy on 27.01.15.
 */
public class AddAlarmCase {

    private static Timeline timeline = new Timeline();
    private static Set<RepeatableAlarm> uiAlarms = new HashSet<RepeatableAlarm>();

    public static void main(String[] arg) {
        System.out.println(timeline);

        RepeatableAlarm repeatableAlarm = getAlarmFromUi();
        List<Alarm> alarms = repeatableAlarm.breakIntoPieces();
        addAlarmsToTimeline(alarms);

        System.out.println(timeline);

        Alarm closest = getClosestAlarmTo(new DateTime().withDate(2015, 1, 28).withTime(10, 0, 0, 0));

        System.out.println(closest);

        addClosestToAlarmManager(closest);

        updateUIListOfAlarms(repeatableAlarm);

    }

    public static void updateUIListOfAlarms(RepeatableAlarm alarm) {
        uiAlarms.add(alarm);
        System.out.println(uiAlarms);
    }

    public static void addClosestToAlarmManager(Alarm closest) {
        // if different than current closest
    }

    public static Alarm getClosestAlarmTo(DateTime current) {
        return timeline.getClosestTo(current);
    }

    public static void addAlarmsToTimeline(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            timeline.addAlarm(alarm);
        }
    }

    public static RepeatableAlarm getAlarmFromUi() {
        RepeatableAlarm result = new RepeatableAlarm(Time.from(8, 15), new int[] {1, 2, 3, 4, 5});

        return result;
    }
}


