package pl.mate00.sleeperfriendlyapp.ui;

import org.joda.time.DateTime;

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

    private Time alarmTime;
    private int[] days;
    private DateTime currentTime;
    private boolean isEnabled;

    public RepeatableAlarm(Time time, int[] days) {
        this(time, days, true);
    }

    public RepeatableAlarm(Time time, int[] days, boolean isEnabled) {
        this.alarmTime = time;
        this.days = days;
        this.isEnabled = isEnabled;
    }

    public RepeatableAlarm(Time time, DateTime currentTime) {
        this(time, new int[0]);
        this.currentTime = currentTime;
    }

    public List<Alarm> breakIntoPieces() {
        List<Alarm> result = new ArrayList<Alarm>();

        if (days.length == 0) {
            if (currentTimeLessThan(alarmTime)) {
                result.add(new Alarm(alarmTime, currentTime.getDayOfWeek()));
            } else {
                result.add(new Alarm(alarmTime, currentTime.plusDays(1).getDayOfWeek()));
            }
        }

        for (int day : days) {
            result.add(new Alarm(alarmTime, day));
        }

        return result;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Time getAlarmTime() {
        return alarmTime;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public int[] getDays() {
        return days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepeatableAlarm that = (RepeatableAlarm) o;

        if (!alarmTime.equals(that.alarmTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return alarmTime.hashCode();
    }

    @Override
    public String toString() {
        return "RepeatableAlarm{" +
                "alarmTime=" + alarmTime +
                ", days=" + Arrays.toString(days) +
                ", " + isEnabled +
                '}';
    }

    public String formatAlarmTime(String timeFormat) {
        DateFormat format = new SimpleDateFormat(timeFormat);
        DateTime dateTime = new DateTime().withHourOfDay(getAlarmTime().getHour()).withMinuteOfHour(getAlarmTime().getMinute());
        return format.format(dateTime.toDate());
    }

    public String formatDays() {
        String result = "";

        String[] week = new String[] {
            "", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
        };

        for (int i = 0; i < days.length; i++) {
            result += week[days[i]];

            if (i < days.length - 1) {
                result += ", ";
            }
        }

        return result;
    }

    private boolean currentTimeLessThan(Time time) {
        int currentHour = currentTime.getHourOfDay();
        int currentMinute = currentTime.getMinuteOfHour();

        return Time.of(currentHour, currentMinute).compareTo(time) == -1;
    }
}
