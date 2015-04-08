package pl.mate00.sleeperfriendlyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Optional;

import org.joda.time.DateTime;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;
import pl.mate00.sleeperfriendlyapp.ui.AlarmManagerMobile;

public class NextAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = NextAlarmReceiver.class.getSimpleName();

    private Context context;

    public NextAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("ALARM_ACTION")) {
            return;
        }

        this.context = context;
        DateTime current = new DateTime();
        Log.d(TAG, "Alarm fired at " + current);

        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();

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
