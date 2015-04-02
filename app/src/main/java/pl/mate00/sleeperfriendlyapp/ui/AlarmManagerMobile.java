package pl.mate00.sleeperfriendlyapp.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.joda.time.DateTime;

import pl.mate00.sleeperfriendlyapp.NextAlarmReceiver;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;

/**
 * Created by mamy on 23.02.15.
 */
public class AlarmManagerMobile {

    private static final int REQUEST_ID = 0;

    private Context context;

    public AlarmManagerMobile(Context context) {
        this.context = context;
    }

    public void updateWithClosestAlarm(Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.toMillis(), getOperation());
    }

    public void cancelClosestAlarm() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getOperation());
    }

    private PendingIntent getOperation() {
        Intent alarmIntent = new Intent(context, NextAlarmReceiver.class);
        alarmIntent.setAction("ALARM_ACTION");
        PendingIntent result = PendingIntent.getBroadcast(context, REQUEST_ID, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        return result;
    }

}