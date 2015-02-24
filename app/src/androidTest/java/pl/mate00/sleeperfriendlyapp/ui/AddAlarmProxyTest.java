package pl.mate00.sleeperfriendlyapp.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.app.AlarmManager;
import android.content.Context;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlarmManager;

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

    @Before
    public void setUp() {
        subject = new AddAlarmProxy(context);
        uiListenerMock = mock(AddAlarmUiCase.class);
        subject.setUiListener(uiListenerMock);
        alarmManager = (AlarmManager) Robolectric.application.getSystemService(Context.ALARM_SERVICE);
        shadowAlarmManager = Robolectric.shadowOf(alarmManager);
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
        subject.addRepeatableAlarm(8, 15, new int[]{}, new DateTime().withDayOfWeek(1).withTime(8, 0, 0, 0));

        verify(uiListenerMock).updateUiWithAlarm(eq(new RepeatableAlarm(Time.of(8, 15), new int[] {1})));
        verify(uiListenerMock, never()).onErrorAfterAdding(anyString());

        subject.addRepeatableAlarm(8, 15, new int[]{}, new DateTime().withDayOfWeek(1).withTime(8, 0, 0, 0));

        verify(uiListenerMock).onErrorAfterAdding(anyString());
        verify(uiListenerMock, times(1)).updateUiWithAlarm(eq(new RepeatableAlarm(Time.of(8, 15), new int[]{1})));
    }

    @Test
    public void addOneSingleAlarmOneMultiOverlapping_ShouldCallErrorCallback() {
        subject.addRepeatableAlarm(8, 15, new int[]{}, new DateTime().withDayOfWeek(1).withTime(8, 0, 0, 0));
        subject.addRepeatableAlarm(8, 15, new int[]{1, 2, 3}, new DateTime().withDayOfWeek(1).withTime(8, 0, 0, 0));

        verify(uiListenerMock, times(1)).updateUiWithAlarm(eq(new RepeatableAlarm(Time.of(8, 15), new int[] {1})));
        verify(uiListenerMock, times(1)).onErrorAfterAdding(anyString());
    }

    @Test
    public void noAlarmShouldBeScheduled() {
        assertNull(shadowAlarmManager.getNextScheduledAlarm());
    }

    @Test
    public void addAlarm_shouldScheduleAlarm() {
        subject.addRepeatableAlarm(8, 15, new int[]{}, new DateTime().withDayOfWeek(1).withTime(8, 0, 0, 0));

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
        assertNotNull(scheduledAlarm);
    }

    @Test
    public void addAlarm_ShouldScheduleAlarmForTheSameDay() {
        subject.addRepeatableAlarm(8, 15, new int[]{}, new DateTime().withDayOfWeek(1).withTime(8, 0, 0, 0));

        ShadowAlarmManager.ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();

        DateTime alarmTime = new DateTime().withMillis(scheduledAlarm.triggerAtTime);

        assertEquals(alarmTime.getDayOfWeek(), 1);
        assertEquals(alarmTime.getHourOfDay(), 8);
        assertEquals(alarmTime.getMinuteOfHour(), 15);
    }
}

