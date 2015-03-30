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

    private Context context;

    public AlarmManagerMobile(Context context) {
        this.context = context;
    }

    public void updateWithClosestAlarm(Alarm alarm) {
        Intent alarmIntent = new Intent(context, NextAlarmReceiver.class);
        alarmIntent.setAction("ALARM_ACTION");
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.toMillis(), operation);
    }

}