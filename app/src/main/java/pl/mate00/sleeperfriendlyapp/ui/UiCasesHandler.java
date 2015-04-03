package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.google.common.base.Optional;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;

/**
 * Created by mamy on 26.02.15.
 */
public class UiCasesHandler {

    private Context context;

    private UiCallbacks listenerUi;

    private Timeline timeline;

    public UiCasesHandler(Context context) {
        this.context = context;
        timeline = new TimelineDb(context);
    }

    public void setUiListener(UiCallbacks listenerUi) {
        this.listenerUi = listenerUi;
    }

    public void addAlarm(int hour, int minute, int[] days, DateTime current) {
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

    public void deleteAlarm(RepeatableAlarm alarm, DateTime current) {
        removeAlarms(alarm.breakIntoPieces());
        listenerUi.updateUiAfterDelete(alarm);
        registerClosestAlarm(current);
    }

    public void setAlarmEnabledTo(RepeatableAlarm repeatableAlarm, boolean isEnabled) {
        for (Alarm alarm : repeatableAlarm.breakIntoPieces()) {
            timeline.removeAlarm(alarm);
            alarm.setEnabled(isEnabled);
            timeline.addAlarm(alarm);
        }
    }

    private void registerClosestAlarm(DateTime currentTime) {
        Optional<Alarm> closest = timeline.getClosestTo(currentTime);
        AlarmManagerMobile manager = new AlarmManagerMobile(context);
        if (closest.isPresent()) {
            manager.updateWithClosestAlarm(closest.get());
        } else {
            manager.cancelClosestAlarm();
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
