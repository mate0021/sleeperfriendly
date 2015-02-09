package pl.mate00.sleeperfriendlyapp.timeline;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.joda.time.DateTimeConstants.FRIDAY;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;
import static org.joda.time.DateTimeConstants.TUESDAY;
import static org.joda.time.DateTimeConstants.WEDNESDAY;

/**
 * Created by mamy on 09.02.15.
 */
public abstract class AbstractTimelineSuite {

    Timeline subject;

    public abstract Timeline getTimelineInstance();

    @Before
    public void setUp() {
        subject = getTimelineInstance();
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
    public void getClosestAlarm_NoAlarmOnTimeline_ShouldReturnNull() {
        Alarm result = subject.getClosestTo(anyDateTime());

        Assert.assertNull(result);
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
        Alarm a1 = new Alarm(Time.of(0, 0), MONDAY);
        Alarm a2 = new Alarm(Time.of(23, 59), SUNDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        DateTime dt = new DateTime();
        dt = dt.withDayOfWeek(TUESDAY).withHourOfDay(10).withMinuteOfHour(00);
        Alarm closestAlarm = subject.getClosestTo(dt);

        Assert.assertEquals(closestAlarm, a2);
    }

    @Test
    public void getClosestAlarm_TwoAlarms_CurrentlyEndOfWeek() {
        Alarm a1 = new Alarm(Time.of(0, 0), MONDAY);
        Alarm a2 = new Alarm(Time.of(23, 59), SUNDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        DateTime dt = new DateTime();
        dt = dt.withDayOfWeek(SUNDAY).withHourOfDay(23).withMinuteOfHour(59);
        Alarm closestAlarm = subject.getClosestTo(dt);

        Assert.assertEquals(a1, closestAlarm);
    }

    @Test
    public void getClosestAlarm_ThreeAlarms_CurrentlyMiddleWeek() {
        Alarm a1 = new Alarm(Time.of(0, 0), MONDAY);
        Alarm a2 = new Alarm(Time.of(8, 0), SUNDAY);
        Alarm a3 = new Alarm(Time.of(23, 59), FRIDAY);
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
        subject.addAlarm(new Alarm(Time.of(18, 12), MONDAY));
        subject.addAlarm(new Alarm(Time.of(8, 15), FRIDAY));
        subject.addAlarm(new Alarm(Time.of(9, 15), WEDNESDAY));

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
    public void shouldReturnNextAvailableAlarmAfterRemovingFirst() {
        Alarm a1 = new Alarm(Time.of(18, 12), TUESDAY);
        Alarm a2 = new Alarm(Time.of(8, 15), FRIDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        DateTime current = new DateTime();
        current = current.withDayOfWeek(MONDAY);

        subject.removeAlarm(a1);

        Alarm expectedAfterRemoval = new Alarm(Time.of(8, 15), FRIDAY);
        Assert.assertEquals(expectedAfterRemoval, subject.getClosestTo(current));
    }

    @Test
    public void shouldNotContainRemovedAlarm() {
        Alarm a1 = new Alarm(Time.of(18, 12), TUESDAY);
        Alarm a2 = new Alarm(Time.of(8, 15), FRIDAY);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        subject.removeAlarm(a1);

        Set<Alarm> alarms = subject.getAllAlarms();
        assertFalse(alarms.contains(a1));
    }

    @Test
    public void shouldReturnEmptySetWhenNoAlarms() {
        Set<Alarm> alarms = subject.getAllAlarms();

        assertTrue(alarms.isEmpty());
    }

    @Test
    public void getClosestAlarm_OneDisabledAlarm_ShouldReturnNull() {
        Alarm a = anyAlarm();
        a.setEnabled(false);

        subject.addAlarm(a);

        Assert.assertNull(subject.getClosestTo(anyDateTime()));
    }

    @Test
    public void addOneDisabledAlarm_IsNotEmpty() {
        Alarm a = anyAlarm();
        a.setEnabled(false);

        subject.addAlarm(a);

        Assert.assertFalse(subject.isEmpty());
    }

    @Test
    public void getClosestAlarm_ShouldReturnOnlyEnabledAlarm() {
        Alarm disabledAlarm = new Alarm(Time.of(8, 0), MONDAY);
        disabledAlarm.setEnabled(false);

        Alarm enabledAlarm = new Alarm(Time.of(8, 0), WEDNESDAY);

        subject.addAlarm(disabledAlarm);
        subject.addAlarm(enabledAlarm);
        DateTime current = new DateTime().withDayOfWeek(MONDAY).withTime(5, 0, 0, 0);

        Alarm result = subject.getClosestTo(current);

        Assert.assertEquals(enabledAlarm, result);
    }

    @Test
    public void getsAllAlarms() {
        Alarm a1 = anyAlarm();
        Alarm a2 = new Alarm(Time.of(3, 3), 1);
        subject.addAlarm(a1);
        subject.addAlarm(a2);

        Set<Alarm> result = subject.getAllAlarms();

        Assert.assertEquals(result.size(), 2);
    }

    @Test
    public void shouldReturnAllAlarms() {
        subject.addAlarm(new Alarm(Time.of(7, 0), 1));
        subject.addAlarm(new Alarm(Time.of(7, 10), 1));
        subject.addAlarm(new Alarm(Time.of(7, 20), 1));

        Set<Alarm> allAlarms = subject.getAllAlarms();

        assertTrue(allAlarms.contains(new Alarm(Time.of(7, 0), 1)));
        assertTrue(allAlarms.contains(new Alarm(Time.of(7, 10), 1)));
        assertTrue(allAlarms.contains(new Alarm(Time.of(7, 20), 1)));
    }

    @Test
    public void shouldAddOnlyUniqueAlarms() {
        subject.addAlarm(anyAlarm());
        subject.addAlarm(anyAlarm());

        assertEquals(1, subject.getNumberOfAlarms());
    }

    @Test
    public void shouldReturnNumberOfAllAddedAlarms() {
        subject.addAlarm(new Alarm(Time.of(8, 0), 1));
        subject.addAlarm(new Alarm(Time.of(8, 15), 1));

        assertEquals(2, subject.getNumberOfAlarms());
    }

    private Alarm anyAlarm() {
        return new Alarm(Time.of(0, 51), Calendar.MONDAY);
    }

    private DateTime anyDateTime() {
        DateTime result = new DateTime();
        result = result.withYear(2015).withMonthOfYear(01).withDayOfMonth(12).withHourOfDay(8).withMinuteOfHour(15);

        return result;
    }
}
