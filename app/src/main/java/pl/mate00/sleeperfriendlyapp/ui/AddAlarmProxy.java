package pl.mate00.sleeperfriendlyapp.ui;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.app.AlarmManager;

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

    public void addRepeatableAlarm(int hour, int minute, int[] days) {
        DateTime current = new DateTime();
        if (days.length == 0) {
            Alarm alarm = new Alarm(Time.of(hour, minute), current.getDayOfWeek());
            try {
                timeline.addAlarm(alarm);
                listenerUi.updateUiWithAlarm(new RepeatableAlarm(Time.of(hour, minute), current));
                registerClosestAlarm(current);
            } catch (SQLiteConstraintException e) {
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
            } catch (SQLiteConstraintException e) {
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


interface AlarmsManager {
    void updateWithClosestAlarm(Alarm alarm);

}

class AlarmManagerMobile implements AlarmsManager {
    private Context context;

    public AlarmManagerMobile(Context context) {
        this.context = context;
    }

    public void updateWithClosestAlarm(Alarm alarm) {
        Intent alarmIntent = new Intent(context, NextAlarmReceiver.class);
        alarmIntent.setAction("ALARM_ACTION");
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmToMillis(alarm), operation);
    }

    private long alarmToMillis(Alarm alarm) {
        System.out.println(" *** closest " + alarm);
        DateTime alarmTime =
                new DateTime().
                        withTime(alarm.getTime().getHour(), alarm.getTime().getMinute(), 0, 0).
                        withDayOfWeek(alarm.getDayOfWeek());
        System.out.println(" **** " + alarmTime);

        return alarmTime.getMillis();
    }
}

