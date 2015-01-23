package pl.mate00.sleeperfriendlyapp.timeline;

import static org.joda.time.DateTimeConstants.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

/**
 * Created by mamy on 15.01.15.
 */
public class TimelineTest {

    private Timeline subject;

    @Before
    public void setUp() throws Exception {
        subject = new Timeline();
    }

    @Test
    public void timeLineIsEmpty() {
        Assert.assertTrue(subject.isEmpty());
    }

    @Test
    public void isNotEmptyAfterAddingAlarm() {
        Alarm a = anyAlarm();
        subject.addAlarm(a);

        Assert.assertFalse(subject.isEmpty());
    }

    @Test
    public void getClosestAlarm_OneAlarmOnTimeline() {
        Alarm a = anyAlarm();
        subject.addAlarm(a);

        Alarm closest = subject.getClosestTo(anyDateTime());

        Assert.assertEquals(a, closest);
    }

    @Test
    public void getClosestAlarm_TwoAlarmsOnTimeline_GetLaterAlarm() {
        Alarm a1 = new Alarm(Time.from(0, 0), MONDAY);
        Alarm a2 = new Alarm(Time.from(23, 59), SUNDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        DateTime dt = new DateTime();
        dt = dt.withDayOfWeek(TUESDAY).withHourOfDay(10).withMinuteOfHour(00);
        Alarm closestAlarm = subject.getClosestTo(dt);

        Assert.assertEquals(closestAlarm, a2);
    }

    @Test
    public void getClosestAlarm_TwoAlarms_CurrentlyEndOfWeek() {
        Alarm a1 = new Alarm(Time.from(0, 0), MONDAY);
        Alarm a2 = new Alarm(Time.from(23, 59), SUNDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        DateTime dt = new DateTime();
        dt = dt.withDayOfWeek(SUNDAY).withHourOfDay(23).withMinuteOfHour(59);
        Alarm closestAlarm = subject.getClosestTo(dt);

        Assert.assertEquals(a1, closestAlarm);
    }

    @Test
    public void getClosestAlarm_ThreeAlarms_CurrentlyMiddleWeek() {
        Alarm a1 = new Alarm(Time.from(0, 0), MONDAY);
        Alarm a2 = new Alarm(Time.from(8, 0), SUNDAY);
        Alarm a3 = new Alarm(Time.from(23, 59), FRIDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);
        subject.addAlarm(a3);

        DateTime dt = new DateTime();
        dt = dt.withDayOfWeek(TUESDAY).withTime(14, 0, 0, 0);

        Alarm closestAlarm = subject.getClosestTo(dt);

        Assert.assertEquals(a3, closestAlarm);
    }

    @Test
    public void addOnlyUniqueAlarms() {
        subject.addAlarm(anyAlarm());
        subject.addAlarm(anyAlarm());

        Assert.assertEquals(1, subject.getNumberOfAlarms());
    }

    @Test
    public void addThreeDifferentAlarms() {
        subject.addAlarm(new Alarm(Time.from(18, 12), MONDAY));
        subject.addAlarm(new Alarm(Time.from(8, 15), FRIDAY));
        subject.addAlarm(new Alarm(Time.from(9, 15), WEDNESDAY));

        int result = subject.getNumberOfAlarms();

        Assert.assertEquals(3, result);
    }

    @Test
    public void noAlarmsWhenRemovedAll() {
        subject.addAlarm(anyAlarm());
        subject.removeAlarm(anyAlarm());

        Assert.assertTrue(subject.isEmpty());
    }

    @Test
    public void timeLineDoesNotHaveRemovedAlarm() {
        Alarm a1 = new Alarm(Time.from(18, 12), TUESDAY);
        Alarm a2 = new Alarm(Time.from(8, 15), FRIDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        DateTime current = new DateTime();
        current = current.withDayOfWeek(MONDAY);

        subject.removeAlarm(a1);

        Alarm expectedAfterRemoval = new Alarm(Time.from(8, 15), FRIDAY);
        Assert.assertEquals(expectedAfterRemoval, subject.getClosestTo(current));
    }

    private Alarm anyAlarm() {
        return new Alarm(Time.from(0, 51), Calendar.MONDAY);
    }

    private DateTime anyDateTime() {
        DateTime result = new DateTime();
        result = result.withYear(2015).withMonthOfYear(01).withDayOfMonth(12).withHourOfDay(8).withMinuteOfHour(15);

        return result;
    }
}
