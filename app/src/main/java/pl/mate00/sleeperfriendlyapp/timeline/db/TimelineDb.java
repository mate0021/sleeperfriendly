package pl.mate00.sleeperfriendlyapp.timeline.db;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

import java.util.Set;

import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

/**
 * Created by mamy on 30.01.15.
 */
public class TimelineDb implements Timeline {

    private TimelineDbHelper dbHelper;
    private SQLiteDatabase dbWrite;

    public TimelineDb(Context context) {
        dbHelper = new TimelineDbHelper(context);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void addAlarm(Alarm alarm) {
        String isEnabled = "1";

        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DAY_OF_WEEK, alarm.getDayOfWeek());
        values.put(COL_HOUR, alarm.getTime().getHour());
        values.put(COL_MINUTE, alarm.getTime().getMinute());
        values.put(COL_ENABLED, isEnabled);
        dbWrite.insert(TABLE_NAME, null, values);
        dbWrite.close();
    }

    @Override
    public Alarm getClosestTo(DateTime currentTime) {
        return null;
    }

    @Override
    public void removeAlarm(Alarm alarm) {
    }

    @Override
    public int getNumberOfAlarms() {
        return 0;
    }

    @Override
    public Set<Alarm> getAllAlarms() {
        return null;
    }

    public static void main(String[] args) {
    }
}
