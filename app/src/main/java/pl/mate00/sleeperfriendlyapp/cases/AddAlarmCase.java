package pl.mate00.sleeperfriendlyapp.cases;

import org.joda.time.DateTimeConstants;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

/**
 * Created by mamy on 27.01.15.
 */
public class AddAlarmCase {

    public static void main(String[] arg) {
        Alarm a1 = getAlarmFromUI();
        addToTimeline(a1);
        
        Alarm closest = getClosestAlarm();
        addClosestToAlarmManager(closest);
        updateUIListOfAlarms();
    }

    private static void updateUIListOfAlarms() {
        
    }

    private static void addClosestToAlarmManager(Alarm closest) {
        // if different than current closest
    }

    private static Alarm getClosestAlarm() {
        return null;
    }

    private static void addToTimeline(Alarm a1) {
        
    }
    
    private static Alarm getAlarmFromUI() {
        return new Alarm(Time.from(8, 15), DateTimeConstants.MONDAY);
    }
}
