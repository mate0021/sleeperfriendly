package pl.mate00.sleeperfriendlyapp.timeline;

/**
 * Created by mamy on 19.01.15.
 */
public class Time implements Comparable<Time> {
    private int hour;
    private int minute;

    private Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static Time from(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Wrong time value.");
        }

        return new Time(hour, minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (hour != time.hour) return false;
        if (minute != time.minute) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        return result;
    }

    @Override
    public int compareTo(Time that) {
        boolean isSmaller = hour < that.hour || (hour == that.hour && minute < that.minute);
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
    public String toString() {
        return "Time{" +
                "h=" + hour +
                ", m=" + minute +
                '}';
    }
}
