package pl.mate00.sleeperfriendlyapp.cases;

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
public class DeleteRepeatableAlarm {

    private static Timeline timeline = new Timeline();
    private static Set<RepeatableAlarm> uiAlarms = new HashSet<>();

    public static void main(String[] args) {
        System.out.println(timeline);
        System.out.println(uiAlarms);

        RepeatableAlarm a1 = new RepeatableAlarm(Time.from(6, 45), new int[] {1, 2, 3});
        RepeatableAlarm a2 = new RepeatableAlarm(Time.from(7, 45), new int[] {1, 2, 3});

        addAlarmsToTimeline(a1.breakIntoPieces());
        addAlarmsToTimeline(a2.breakIntoPieces());

        updateUIListOfAlarms(a1);
        updateUIListOfAlarms(a2);

        System.out.println(timeline);
        System.out.println(uiAlarms);

        deleteRepeatableAlarm(a1);

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
        System.out.println(uiAlarms);
    }

    public static void deleteRepeatableAlarm(RepeatableAlarm alarm) {
        for (Alarm a : alarm.breakIntoPieces()) {
            timeline.removeAlarm(a);
        }
        uiAlarms.remove(alarm);
    }
}
