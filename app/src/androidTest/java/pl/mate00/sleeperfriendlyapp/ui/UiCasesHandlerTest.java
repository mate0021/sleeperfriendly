package pl.mate00.sleeperfriendlyapp.ui;

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
import org.robolectric.shadows.ShadowAlarmManager.ScheduledAlarm;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

import static org.joda.time.DateTimeConstants.FRIDAY;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;
import static org.joda.time.DateTimeConstants.THURSDAY;
import static org.joda.time.DateTimeConstants.TUESDAY;
import static org.joda.time.DateTimeConstants.WEDNESDAY;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, reportSdk = 18)
public class UiCasesHandlerTest {

    private UiCasesHandler subject;

    private Context context = Robolectric.application;

    private DateTime currentWednesday10Am;

    private UiCallbacks uiListenerMock;

    private AlarmManager alarmManager;

    private ShadowAlarmManager shadowAlarmManager;

    @Before
    public void setUp() {
        subject = new UiCasesHandler(context);
        currentWednesday10Am = new DateTime().withDayOfWeek(WEDNESDAY).withTime(10, 0, 0, 0);
        alarmManager = (AlarmManager) Robolectric.application.getSystemService(Context.ALARM_SERVICE);
        shadowAlarmManager = Robolectric.shadowOf(alarmManager);
        uiListenerMock = mock(UiCallbacks.class);
        subject.setUiListener(uiListenerMock);
    }

    @Test
    public void addAlarm_NoDays_ShouldNotCallErrorCallback() {
        subject.addAlarm(8, 15, new int[]{}, new DateTime());
        verify(uiListenerMock, never()).onErrorAfterAdding(anyString());
    }

    @Test
    public void addTwoTheSameAlarms_NoDays_ShouldCallErrorCallback() {
        subject.addAlarm(8, 15, new int[]{}, currentWednesday10Am);

        verify(uiListenerMock).updateUiWithAlarm(eq(new RepeatableAlarm(Time.of(8, 15), new int[] {1})));
        verify(uiListenerMock, never()).onErrorAfterAdding(anyString());

        subject.addAlarm(8, 15, new int[]{}, currentWednesday10Am);

        verify(uiListenerMock).onErrorAfterAdding(anyString());
        verify(uiListenerMock, times(1)).updateUiWithAlarm(
                eq(new RepeatableAlarm(Time.of(8, 15), new int[]{1})));
    }

    @Test
    public void addOneSingleAlarmOneMultiOverlapping_ShouldCallErrorCallback_MultiIsNotAdded() {
        subject.addAlarm(8, 15, new int[]{}, currentWednesday10Am);
        subject.addAlarm(8, 15, new int[]{WEDNESDAY, THURSDAY, FRIDAY}, currentWednesday10Am);

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
        subject.addAlarm(8, 15, new int[]{}, currentWednesday10Am);

        ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
        assertNotNull(scheduledAlarm);
    }

    @Test
    public void addAlarm_ShouldScheduleAlarmForTheSameDay() {
        subject.addAlarm(12, 15, new int[]{}, currentWednesday10Am);

        assertScheduledAlarmEquals(WEDNESDAY, 12, 15);
    }

    @Test
    public void addAlarm_ShouldScheduleAlarmForNextDay() {
        subject.addAlarm(8, 0, new int[] {}, currentWednesday10Am);

        assertScheduledAlarmEquals(THURSDAY, 8, 0);
    }

    @Test
    public void addAlarm_TwoAlarms_FirstSunday_ThenFriday_ShouldScheduleFriday() {
        subject.addAlarm(8, 0, new int[] {SUNDAY}, currentWednesday10Am);
        subject.addAlarm(8, 0, new int[] {FRIDAY}, currentWednesday10Am);

        assertScheduledAlarmEquals(FRIDAY, 8, 0);
    }

    @Test
    public void addAlarm_TwoAlarms_FirstFriday_ThenSunday_ShouldScheduleFriday() {
        subject.addAlarm(8, 0, new int[] {FRIDAY}, currentWednesday10Am);
        subject.addAlarm(8, 0, new int[] {SUNDAY}, currentWednesday10Am);

        assertScheduledAlarmEquals(FRIDAY, 8, 0);
    }

    @Test
    public void addAlarm_TwoAlarms_NextWeek_FirstMonday_ThenTuesday_ShouldSchedule_Monday() {
        subject.addAlarm(8, 0, new int[] {MONDAY}, currentWednesday10Am);
        subject.addAlarm(8, 0, new int[] {TUESDAY}, currentWednesday10Am);

        assertScheduledAlarmEquals(MONDAY, 8, 0);
    }

    @Test
    public void deleting_addTwoAlarms_DeleteEarlier_ShouldScheduleLater() {
        subject.addAlarm(8, 0, new int[] {THURSDAY}, currentWednesday10Am);
        subject.addAlarm(8, 0, new int[] {FRIDAY}, currentWednesday10Am);


        RepeatableAlarm alarm = new RepeatableAlarm(Time.of(8, 0), new int[] { THURSDAY });
        subject.deleteAlarm(alarm,  currentWednesday10Am);

        assertScheduledAlarmEquals(FRIDAY, 8, 0);
    }

    @Test
    public void deleting_addOneAlarm_DeleteIt_ShouldNotScheduleAnything() {
        subject.addAlarm(8, 0, new int[] {THURSDAY}, currentWednesday10Am);

        RepeatableAlarm alarm = new RepeatableAlarm(Time.of(8, 0), new int[] { THURSDAY });
        subject.deleteAlarm(alarm,  currentWednesday10Am);

        ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();

        assertNull(scheduledAlarm);
    }

    @Test
    public void deleting_EarlierAlarm_LaterAlarm_DeleteLater_ShouldScheduleEarlier() {
        subject.addAlarm(8, 0, new int[] {THURSDAY}, currentWednesday10Am);
        subject.addAlarm(8, 0, new int[] {FRIDAY}, currentWednesday10Am);

        RepeatableAlarm alarm = new RepeatableAlarm(Time.of(8, 0), new int[] {FRIDAY});
        subject.deleteAlarm(alarm, currentWednesday10Am);

        assertScheduledAlarmEquals(THURSDAY,  8, 0);
    }

    @Test
    public void deleting_EarlierAlarm_LaterAlarm_DeleteEarlier_ShouldScheduleLater() {
        subject.addAlarm(8, 0, new int[] {THURSDAY}, currentWednesday10Am);
        subject.addAlarm(8, 0, new int[] {FRIDAY}, currentWednesday10Am);

        RepeatableAlarm alarm = new RepeatableAlarm(Time.of(8, 0), new int[] {THURSDAY});
        subject.deleteAlarm(alarm, currentWednesday10Am);

        assertScheduledAlarmEquals(FRIDAY,  8, 0);
    }

    private void assertScheduledAlarmEquals(int day, int hour, int minute) {
        ScheduledAlarm scheduledAlarm = shadowAlarmManager.getNextScheduledAlarm();
        DateTime alarmTime = new DateTime().withMillis(scheduledAlarm.triggerAtTime);
        assertEquals(alarmTime.getDayOfWeek(), day);
        assertEquals(alarmTime.getHourOfDay(), hour);
        assertEquals(alarmTime.getMinuteOfHour(), minute);
    }
}