package pl.mate00.sleeperfriendlyapp.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.joda.time.DateTimeConstants.*;

import android.app.AlarmManager;
import android.content.Context;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlarmManager;

import java.util.List;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

/**
 * Created by mamy on 24.02.15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, reportSdk = 18)
public class AddAlarmProxyTest {

    private AddAlarmProxy subject;

    private Context context = Robolectric.application;

    private AddAlarmUiCase uiListenerMock;

    private ShadowAlarmManager shadowAlarmManager;

    private AlarmManager alarmManager;
    
    private DateTime currentWednesday10Am;

    @Before
    public void setUp() {
        subject = new AddAlarmProxy(context);
        uiListenerMock = mock(AddAlarmUiCase.class);
        subject.setUiListener(uiListenerMock);
        alarmManager = (AlarmManager) Robolectric.application.getSystemService(Context.ALARM_SERVICE);
        shadowAlarmManager = Robolectric.shadowOf(alarmManager);
        currentWednesday10Am = new DateTime().withDayOfWeek(WEDNESDAY).withTime(10, 0, 0, 0);
    }

    @Test
    public void addAlarm_NoDays_ShouldNotCallErrorCallback() {
        subject.setUiListener(new AddAlarmUiCase() {
            @Override
            public void updateUiWithAlarm(RepeatableAlarm alarm) {
            }

            @Override
            public void onErrorAfterAdding(String message) {
                fail("Should not display fail message");
            }
        });
        subject.addRepeatableAlarm(8, 15, new int[]{}, new DateTime());
    }

    @Test
    public void addTwoTheSameAlarms_NoDays_ShouldCallErrorCallback() {
        subject.addRepeatableAlarm(8, 15, new int[]{}, currentWednesday10Am);

        verify(uiListenerMock).updateUiWithAlarm(eq(new RepeatableAlarm(Time.of(8, 15), new int[] {1})));
        verify(uiListenerMock, never()).onErrorAfterAdding(anyString());

        subject.addRepeatableAlarm(8, 15, new int[]{}, currentWednesday10Am);

        verify(uiListenerMock).onErrorAfterAdding(anyString());
        verify(uiListenerMock, times(1)).updateUiWithAlarm(
                eq(new RepeatableAlarm(Time.of(8, 15), new int[]{1})));
    }

    @Test
    public void addOneSingleAlarmOneMultiOverlapping_ShouldCallErrorCallback_MultiIsNotAdded() {
        subject.addRepeatableAlarm(8, 15, new int[]{}, currentWednesday10Am);
        subject.addRepeatableAlarm(8, 15, new int[]{WEDNESDAY, THURSDAY, FRIDAY}, currentWednesday10Am);

        verify(uiListenerMock, times(1)).updateUiWithAlarm(
                eq(new RepeatableAlarm(Time.of(8, 15), new int[] {THURSDAY})));
        verify(uiListenerMock, times(1)).onErrorAfterAdding(anyString());
    }

    @Test
    public void noAlarmShouldBeScheduled() {
        assertNull(shadowAlarmManager.getNextScheduledAlarm());
    }

    @Test
    public void addAlarm_ShouldScheduleAlarm() {
        subject.addRepeatableAlarm(8, 15, new int[]{}, currentWednesday10Am);

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
        assertNotNull(scheduledAlarm);
    }

    @Test
    public void addAlarm_ShouldScheduleAlarmForTheSameDay() {
        subject.addRepeatableAlarm(12, 15, new int[]{}, currentWednesday10Am);

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();

        DateTime alarmTime = new DateTime().withMillis(scheduledAlarm.triggerAtTime);

        assertEquals(alarmTime.getDayOfWeek(), WEDNESDAY);
        assertEquals(alarmTime.getHourOfDay(), 12);
        assertEquals(alarmTime.getMinuteOfHour(), 15);
    }

    @Test
    public void addAlarm_ShouldScheduleAlarmForNextDay() {
        subject.addRepeatableAlarm(8, 0, new int[] {}, currentWednesday10Am);

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();

        DateTime alarmTime = new DateTime().withMillis(scheduledAlarm.triggerAtTime);

        assertEquals(alarmTime.getDayOfWeek(), THURSDAY);
        assertEquals(alarmTime.getHourOfDay(), 8);
        assertEquals(alarmTime.getMinuteOfHour(), 0);
    }

    @Test
    public void addAlarm_TwoAlarms_FirstSunday_ThenFriday_ShouldScheduleFriday() {
        subject.addRepeatableAlarm(8, 0, new int[] {SUNDAY}, currentWednesday10Am);
        subject.addRepeatableAlarm(8, 0, new int[] {FRIDAY}, currentWednesday10Am);

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();

        DateTime alarmTime = new DateTime().withMillis(scheduledAlarm.triggerAtTime);
        assertEquals(alarmTime.getDayOfWeek(), FRIDAY);
        assertEquals(alarmTime.getHourOfDay(), 8);
        assertEquals(alarmTime.getMinuteOfHour(), 0);
    }

    @Test
    public void addAlarm_TwoAlarms_FirstFriday_ThenSunday_ShouldScheduleFriday() {
        subject.addRepeatableAlarm(8, 0, new int[] {FRIDAY}, currentWednesday10Am);
        subject.addRepeatableAlarm(8, 0, new int[] {SUNDAY}, currentWednesday10Am);

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();

        DateTime alarmTime = new DateTime().withMillis(scheduledAlarm.triggerAtTime);
        assertEquals(alarmTime.getDayOfWeek(), FRIDAY);
        assertEquals(alarmTime.getHourOfDay(), 8);
        assertEquals(alarmTime.getMinuteOfHour(), 0);
    }

    @Test
    public void addAlarm_TwoAlarms_NextWeek_FirstMonday_ThenTuesday_ShouldSchedule_Monday() {
        subject.addRepeatableAlarm(8, 0, new int[] {MONDAY}, currentWednesday10Am);
        subject.addRepeatableAlarm(8, 0, new int[] {TUESDAY}, currentWednesday10Am);

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();

        DateTime alarmTime = new DateTime().withMillis(scheduledAlarm.triggerAtTime);
        assertEquals(alarmTime.getDayOfWeek(), MONDAY);
        assertEquals(alarmTime.getHourOfDay(), 8);
        assertEquals(alarmTime.getMinuteOfHour(), 0);
    }
}

