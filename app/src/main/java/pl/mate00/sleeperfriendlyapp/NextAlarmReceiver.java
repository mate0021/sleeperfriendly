package pl.mate00.sleeperfriendlyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.common.base.Optional;

import org.joda.time.DateTime;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;
import pl.mate00.sleeperfriendlyapp.ui.AlarmManagerMobile;
import pl.mate00.sleeperfriendlyapp.ui.AlarmReaction;
import pl.mate00.sleeperfriendlyapp.ui.dialog.SimpleDialogAlarmReaction;

public class NextAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = NextAlarmReceiver.class.getSimpleName();

    private Context context;

    private AlarmReaction alarmReaction;

    public NextAlarmReceiver() {
        alarmReaction = new SimpleDialogAlarmReaction();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("ALARM_ACTION")) {
            return;
        }

        this.context = context;
        DateTime current = new DateTime();
        Log.d(TAG, " [][][] Alarm fired at " + current);

        alarmReaction.onAlarmFired(context);

        updateWithNextAlarm(current);
    }

    private void updateWithNextAlarm(DateTime current) {
        Timeline timeline = new TimelineDb(context);
        Optional<Alarm> nextAlarm = timeline.getClosestTo(current);
        if (nextAlarm.isPresent()) {
            AlarmManagerMobile alarmManager = new AlarmManagerMobile(context);
            alarmManager.updateWithClosestAlarm(nextAlarm.get());
        }
    }
}
