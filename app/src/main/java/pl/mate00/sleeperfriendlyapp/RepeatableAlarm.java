package pl.mate00.sleeperfriendlyapp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private DateTime currentTime;
    private boolean isEnabled;

    public RepeatableAlarm(Time time, int[] days) {
        this.time = time;
        this.days = days;
        isEnabled = true;
    }

    public RepeatableAlarm(Time time, DateTime currentTime) {
        this.time = time;
        this.currentTime = currentTime;
        isEnabled = true;
    }

    public List<Alarm> breakIntoPieces() {
        List<Alarm> result = new ArrayList<Alarm>(days.length);
        for (int day : days) {
            result.add(new Alarm(time, day));
        }

        return result;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Time getTime() {
        return time;
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

    public String formatAlarmTime(String timeFormat) {
        DateFormat format = new SimpleDateFormat(timeFormat);
        DateTime dateTime = new DateTime().withHourOfDay(getTime().getHour()).withMinuteOfHour(getTime().getMinute());
        return format.format(dateTime.toDate());
    }

    public String formatDays() {
        String result = "";

        String[] week = new String[] {
            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
        };

        for (int i = 0; i < days.length; i++) {
            result += week[days[i] - 1];

            if (i < days.length - 1) {
                result += ", ";
            }
        }

        return result;
    }
}
