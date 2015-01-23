package pl.mate00.sleeperfriendlyapp.timeline;

import static org.joda.time.DateTimeConstants.*;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mamy on 19.01.15.
 */
public class AlarmTest {

    @Test
    public void comparison_SmallerDayOfWeek() {
        Alarm a1 = new Alarm(Time.from(8, 0), MONDAY);
        Alarm a2 = new Alarm(Time.from(8, 0), TUESDAY);

        Assert.assertEquals(a1.compareTo(a2), -1);
    }

    @Test
    public void comparison_GreaterDayOfWeek() {
        Alarm a1 = new Alarm(Time.from(8, 0), WEDNESDAY);
        Alarm a2 = new Alarm(Time.from(8, 0), TUESDAY);

        Assert.assertEquals(a1.compareTo(a2), 1);
    }

    @Test
    public void comparison_Equal() {
        Alarm a1 = new Alarm(Time.from(8, 0), WEDNESDAY);
        Alarm a2 = new Alarm(Time.from(8, 0), WEDNESDAY);

        Assert.assertEquals(a1.compareTo(a2), 0);
    }

    @Test
    public void comparison_SmallerTime() {
        Alarm a1 = new Alarm(Time.from(7, 0), TUESDAY);
        Alarm a2 = new Alarm(Time.from(8, 0), TUESDAY);

        Assert.assertEquals(a1.compareTo(a2), -1);
    }

    @Test
    public void comparison_GreaterTime() {
        Alarm a1 = new Alarm(Time.from(10, 0), TUESDAY);
        Alarm a2 = new Alarm(Time.from(8, 0), TUESDAY);

        Assert.assertEquals(a1.compareTo(a2), 1);
    }

    @Test
    public void isLaterThan_True_DayOfWeek() {
        Alarm a = new Alarm(Time.from(6, 50), TUESDAY);
        DateTime currentTime = new DateTime();
        currentTime = currentTime.withDayOfWeek(MONDAY);

        Assert.assertTrue(a.isLaterThan(currentTime));
    }

    @Test
    public void isLaterThan_False_DayOfWeek() {
        Alarm a = new Alarm(Time.from(6, 50), TUESDAY);
        DateTime currentTime = new DateTime();
        currentTime = currentTime.withDayOfWeek(FRIDAY);

        Assert.assertFalse(a.isLaterThan(currentTime));
    }

    @Test
    public void isLaterThan_True_Time() {
        Alarm a = new Alarm(Time.from(6, 50), TUESDAY);
        DateTime currentTime = new DateTime();
        currentTime = currentTime.withDayOfWeek(TUESDAY).withTime(0, 0, 0, 0);

        Assert.assertTrue(a.isLaterThan(currentTime));
    }

    @Test
    public void isLaterThan_False_Time() {
        Alarm a = new Alarm(Time.from(6, 50), TUESDAY);
        DateTime currentTime = new DateTime();
        currentTime = currentTime.withDayOfWeek(TUESDAY).withTime(8, 0, 0, 0);

        Assert.assertFalse(a.isLaterThan(currentTime));
    }
}
