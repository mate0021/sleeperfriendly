package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.db.DbConstants;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;
import pl.mate00.sleeperfriendlyapp.ui.db.UiAlarmDbHelper;

/**
 * Created by mamy on 16.02.15.
 */
public class AddAlarmProxy {

    private Timeline timeline;

    private AddAlarmUiCase listener;

    public AddAlarmProxy(Context context) {
        this.timeline = new TimelineDb(context);
    }

    public void setUiListener(AddAlarmUiCase listener) {
        this.listener = listener;
    }

    public void addRepeatableAlarm(int hour, int minute, int[] days) {
        if (days.length == 0) {
            DateTime current = new DateTime();
            Alarm alarm = new Alarm(Time.of(hour, minute), current.getDayOfWeek());
            try {
                timeline.addAlarm(alarm);
                listener.updateUiWithAlarm(new RepeatableAlarm(Time.of(hour, minute), current));
            } catch (SQLiteConstraintException e) {
                listener.onErrorAfterAdding("Alarm already exists.");
            }
        } else {
            if (insertOrRollBack(hour, minute, days)) {
                listener.updateUiWithAlarm(new RepeatableAlarm(Time.of(hour, minute), days));
            } else {
                listener.onErrorAfterAdding("Part of alarm already exists.");
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

    public List<RepeatableAlarm> restoreUiAlarms() {
        List<RepeatableAlarm> alarms = new ArrayList<RepeatableAlarm>();

        alarms.add(new RepeatableAlarm(Time.of(8, 0), new int[] {1, 2, 3, 4, 5}));
        alarms.add(new RepeatableAlarm(Time.of(9, 0), new int[] {6, 7}));

        return alarms;
    }

    public void addUiAlarm(RepeatableAlarm alarm) {

    }
}

class UiAlarmState {

    private UiAlarmDbHelper dbHelper;
    private Context context;

    public UiAlarmState(Context context) {
        this.context = context;
    }

    public void addUiAlarm(RepeatableAlarm alarm) {
    }

    public List<RepeatableAlarm> restoreUiAlarms() {
        return new ArrayList<RepeatableAlarm>();
    }

}
