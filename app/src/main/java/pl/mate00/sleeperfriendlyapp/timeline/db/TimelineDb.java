package pl.mate00.sleeperfriendlyapp.timeline.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

import java.util.Set;
import java.util.TreeSet;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.ALARM_COUNT;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.ALL_ALARMS;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.COL_ALARM_COUNT;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.COL_DAY_OF_WEEK;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.COL_ENABLED;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.COL_HOUR;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.COL_MINUTE;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.TABLE_NAME;

/**
 * Created by mamy on 30.01.15.
 */
public class TimelineDb implements Timeline {

    private TimelineDbHelper dbHelper;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;

    public TimelineDb(Context context) {
        dbHelper = new TimelineDbHelper(context);
    }

    @Override
    public boolean isEmpty() {
        return getNumberOfAlarms() == 0;
    }

    @Override
    public void addAlarm(Alarm alarm) {
        String isEnabled = alarm.isEnabled() ? "1" : "0";

        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DAY_OF_WEEK, alarm.getDayOfWeek());
        values.put(COL_HOUR, alarm.getTime().getHour());
        values.put(COL_MINUTE, alarm.getTime().getMinute());
        values.put(COL_ENABLED, isEnabled);
        dbWrite.insert(TABLE_NAME, null, values);
    }

    @Override
    public Alarm getClosestTo(DateTime currentTime) {
        Set<Alarm> allAlarms = getAllAlarms();
        Alarm result = getFirstEnabledAlarm(allAlarms);

        for (Alarm a : allAlarms) {
            if (a.isLaterThan(currentTime) && a.isEnabled()) {
                result = a;
                break;
            }
        }

        return result;
    }

    @Override
    public void removeAlarm(Alarm alarm) {
        dbWrite = dbHelper.getWritableDatabase();
        String whereClause = COL_DAY_OF_WEEK + " = ? and " + COL_HOUR + " = ? and " + COL_MINUTE + " = ? ";
        String[] whereValues = new String[] {
                String.valueOf(alarm.getDayOfWeek()),
                String.valueOf(alarm.getTime().getHour()),
                String.valueOf(alarm.getTime().getMinute())
        };
        dbWrite.delete(TABLE_NAME, whereClause, whereValues);
    }

    @Override
    public int getNumberOfAlarms() {
        dbRead = dbHelper.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery(ALARM_COUNT, null);
        cursor.moveToFirst();
        int size = cursor.getInt(cursor.getColumnIndex(COL_ALARM_COUNT));

        return size;
    }

    @Override
    public Set<Alarm> getAllAlarms() {
        dbRead = dbHelper.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery(ALL_ALARMS, null);
        cursor.moveToFirst();

        Set<Alarm> result = new TreeSet<>();

        while (!cursor.isAfterLast()) {
            int day = cursor.getInt(cursor.getColumnIndex(COL_DAY_OF_WEEK));
            int hour = cursor.getInt(cursor.getColumnIndex(COL_HOUR));
            int minute = cursor.getInt(cursor.getColumnIndex(COL_MINUTE));
            boolean enabled = isEnabled(cursor.getInt(cursor.getColumnIndex(COL_ENABLED)));

            Alarm alarm = new Alarm(Time.of(hour, minute), day, enabled);
            result.add(alarm);
            cursor.moveToNext();
        }
        return result;
    }

    private Alarm getFirstEnabledAlarm(Set<Alarm> alarms) {
        Alarm result = null;
        for (Alarm alarm : alarms) {
            if (alarm.isEnabled()) {
                result = alarm;
                break;
            }
        }

        return result;
    }

    private boolean isEnabled(int flag) {
        return flag != 0;
    }
}
