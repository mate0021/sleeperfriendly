package pl.mate00.sleeperfriendlyapp.timeline;

import org.joda.time.DateTime;

/**
 * Created by mamy on 15.01.15.
 */
public class Alarm implements Comparable<Alarm> {

    private int dayOfWeek;
    private Time time;

    public Alarm(Time time, int dayOfWeek) {
        this.time = time;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public int compareTo(Alarm that) {
        boolean isSmaller = dayOfWeek < that.dayOfWeek || (dayOfWeek == that.dayOfWeek && time.compareTo(that.time) == -1);
        boolean areEqual = equals(that);
        if (isSmaller) {
            return -1;
        } else if (areEqual) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alarm alarm = (Alarm) o;

        if (dayOfWeek != alarm.dayOfWeek) return false;
        if (!time.equals(alarm.time)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dayOfWeek;
        result = 31 * result + time.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "dayOfWeek=" + dayOfWeek +
                ", time=" + time +
                '}';
    }

    public boolean isLaterThan(DateTime argTime) {
        int currentDay = argTime.getDayOfWeek();
        Time currentTime = Time.from(argTime.getHourOfDay(), argTime.getMinuteOfHour());
        if (currentDay < dayOfWeek) {
            return true;
        } else if (currentDay == dayOfWeek && currentTime.compareTo(time) == -1) {
            return true;
        } else {
            return false;
        }
    }
}

