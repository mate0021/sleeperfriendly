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

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;

/**
 * Created by mamy on 16.02.15.
 */
public class AddAlarmProxy {

    private Timeline timeline;

    private AddAlarmUiCase listenerUi;

    public AddAlarmProxy(Context context) {
        this.timeline = new TimelineDb(context);
    }

    public void setUiListener(AddAlarmUiCase listenerUi) {
        this.listenerUi = listenerUi;
    }

    public void addRepeatableAlarm(int hour, int minute, int[] days) {
        if (days.length == 0) {
            DateTime current = new DateTime();
            Alarm alarm = new Alarm(Time.of(hour, minute), current.getDayOfWeek());
            try {
                timeline.addAlarm(alarm);
                listenerUi.updateUiWithAlarm(new RepeatableAlarm(Time.of(hour, minute), current));
            } catch (SQLiteConstraintException e) {
                listenerUi.onErrorAfterAdding("Alarm already exists.");
            }
        } else {
            if (insertOrRollBack(hour, minute, days)) {
                listenerUi.updateUiWithAlarm(new RepeatableAlarm(Time.of(hour, minute), days));
            } else {
                listenerUi.onErrorAfterAdding("Part of alarm already exists.");
            }
        }
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
    private Intent alarmReceiverIntent;

    public void updateWithClosestAlarm(Alarm alarm) {
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmReceiverIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmToMillis(alarm), operation);
    }

    private long alarmToMillis(Alarm alarm) {
        return 0;
    }
}

class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
