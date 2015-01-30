package pl.mate00.sleeperfriendlyapp.timeline.db;

import org.joda.time.DateTime;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

/**
 * Created by mamy on 30.01.15.
 */
public class TimelineDb implements Timeline {
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void addAlarm(Alarm alarm) {

    }

    @Override
    public Alarm getClosestTo(DateTime currentTime) {
        return null;
    }

    @Override
    public void removeAlarm(Alarm alarm) {

    }

    @Override
    public int getNumberOfAlarms() {
        return 0;
    }
}
