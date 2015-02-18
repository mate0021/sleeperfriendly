package pl.mate00.sleeperfriendlyapp;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

import static junit.framework.Assert.assertEquals;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;
import static org.joda.time.DateTimeConstants.TUESDAY;

/**
 * Created by mamy on 11.02.15.
 */
public class RepeatableAlarmTest {

    private RepeatableAlarm subject;

    @Test
    public void testFormatDays_OneDay() {
        subject = new RepeatableAlarm(Time.of(8, 15), new int[] {1});

        assertEquals("Mon", subject.formatDays());
    }

    @Test
    public void testFormatDays_ManyDays() {
        subject = new RepeatableAlarm(Time.of(8, 15), new int[] {1, 2, 3});

        assertEquals("Mon, Tue, Wed", subject.formatDays());
    }

    @Test
    public void testFormatDays_WholeWeek() {
        subject = new RepeatableAlarm(Time.of(8, 15), new int[] {1, 2, 3, 4, 5, 6, 7});

        assertEquals("Mon, Tue, Wed, Thu, Fri, Sat, Sun", subject.formatDays());
    }

    @Test
    public void testFormatDays_NoDays_ShouldReturnEmptyString() {
        subject = new RepeatableAlarm(Time.of(8, 15), new int[] {});

        assertEquals("", subject.formatDays());
    }

    @Test
    public void shouldReturnThreeEnabledAlarms() {
        subject = new RepeatableAlarm(Time.of(8, 15), new int[] {1, 2, 3});

        List<Alarm> expected = new ArrayList<Alarm>();
        expected.add(new Alarm(Time.of(8, 15), 1, true));
        expected.add(new Alarm(Time.of(8, 15), 2, true));
        expected.add(new Alarm(Time.of(8, 15), 3, true));

        assertEquals(expected, subject.breakIntoPieces());
    }

    @Test
    public void shouldReturnAlarmAtNearestTimePossible_TheSameDay() {
        DateTime currentDate = new DateTime().withDayOfWeek(MONDAY).withTime(6, 0, 0, 0);
        subject = new RepeatableAlarm(Time.of(8, 15), currentDate);

        List<Alarm> expected = new ArrayList<Alarm>();
        expected.add(new Alarm(Time.of(8, 15), MONDAY));

        assertEquals(expected, subject.breakIntoPieces());
    }

    @Test
    public void shouldReturnAlarmAtNearestTimePossible_NextDay() {
        DateTime currentDate = new DateTime().withDayOfWeek(MONDAY).withTime(9, 0, 0, 0);
        subject = new RepeatableAlarm(Time.of(8, 15), currentDate);

        List<Alarm> expected = new ArrayList<Alarm>();
        expected.add(new Alarm(Time.of(8, 15), TUESDAY));

        assertEquals(expected, subject.breakIntoPieces());
    }

    @Test
    public void shouldReturnAlarmAtNearestTimePossible_NextWeek() {
        DateTime currentDate = new DateTime().withDayOfWeek(SUNDAY).withTime(21, 0, 0, 0);
        subject = new RepeatableAlarm(Time.of(8, 15), currentDate);

        List<Alarm> expected = new ArrayList<Alarm>();
        expected.add(new Alarm(Time.of(8, 15), MONDAY));

        assertEquals(expected, subject.breakIntoPieces());
    }
}
