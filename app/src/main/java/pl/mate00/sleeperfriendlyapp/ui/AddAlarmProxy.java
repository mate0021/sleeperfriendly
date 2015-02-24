package pl.mate00.sleeperfriendlyapp.ui;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.app.AlarmManager;
import android.database.sqlite.SQLiteException;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.NextAlarmReceiver;
import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;

/**
 * Created by mamy on 16.02.15.
 */
public class AddAlarmProxy {

    private Context context;

    private Timeline timeline;

    private AddAlarmUiCase listenerUi;

    public AddAlarmProxy(Context context) {
        this.timeline = new TimelineDb(context);
        this.context = context;
    }

    public void setUiListener(AddAlarmUiCase listenerUi) {
        this.listenerUi = listenerUi;
    }

    public void addRepeatableAlarm(int hour, int minute, int[] days, DateTime current) {
        if (days.length == 0) {
            Time uiTime = Time.of(hour, minute);
            Time currentTime = Time.of(current.getHourOfDay(), current.getMinuteOfHour());
            int dayOfWeek = currentTime.compareTo(uiTime) < 0 ? current.getDayOfWeek() : current.plusDays(1).getDayOfWeek();
            Alarm alarm = new Alarm(uiTime, dayOfWeek);
            try {
                timeline.addAlarm(alarm);
                listenerUi.updateUiWithAlarm(new RepeatableAlarm(Time.of(hour, minute), new int[]{dayOfWeek}));
                registerClosestAlarm(current);
            } catch (SQLiteException e) {
                listenerUi.onErrorAfterAdding("Alarm already exists.");
            }
        } else {
            if (insertOrRollBack(hour, minute, days)) {
                listenerUi.updateUiWithAlarm(new RepeatableAlarm(Time.of(hour, minute), days));
                registerClosestAlarm(current);
            } else {
                listenerUi.onErrorAfterAdding("Part of alarm already exists.");
            }
        }
    }

    private void registerClosestAlarm(DateTime currentTime) {
        Alarm closest = timeline.getClosestTo(currentTime);
        AlarmManagerMobile manager = new AlarmManagerMobile(context);
        manager.updateWithClosestAlarm(closest);
    }

    private boolean insertOrRollBack(int hour, int minute, int[] days) {
        boolean isSuccessful = true;
        RepeatableAlarm repeatableAlarm = new RepeatableAlarm(Time.of(hour, minute), days);
        List<Alarm> alreadyAdded = new ArrayList<>();

        for (Alarm a : repeatableAlarm.breakIntoPieces()) {
            try {
                timeline.addAlarm(a);
                alreadyAdded.add(a);
            } catch (SQLiteException e) {
                isSuccessful = false;
                removeAlarms(alreadyAdded);
                break;
            }
        }

        return isSuccessful;
    }

    private void removeAlarms(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            timeline.removeAlarm(alarm);
        }
    }
}
