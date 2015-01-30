package pl.mate00.sleeperfriendlyapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

/**
 * Created by mamy on 30.01.15.
 */
public class RepeatableAlarm {
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

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
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

    @Override
    public String toString() {
        return "RepeatableAlarm{" +
                "time=" + time +
                ", days=" + Arrays.toString(days) +
                ", " + isEnabled +
                '}';
    }
}
