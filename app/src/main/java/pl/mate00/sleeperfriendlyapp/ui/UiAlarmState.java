package pl.mate00.sleeperfriendlyapp.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.ui.db.UiAlarmDbHelper;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_DAYS;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_ENABLED;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_HOUR;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_MINUTE;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.TABLE_NAME;

public class UiAlarmState {

    private UiAlarmDbHelper dbHelper;
    private SQLiteDatabase dbRead;
    private SQLiteDatabase dbWrite;

    public UiAlarmState(Context context) {
        this.dbHelper = new UiAlarmDbHelper(context);
    }

    public void addUiAlarm(RepeatableAlarm alarm) {
        dbWrite = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_HOUR, alarm.getAlarmTime().getHour());
        values.put(COL_MINUTE, alarm.getAlarmTime().getMinute());
        values.put(COL_DAYS, daysToString(alarm.getDays()));
        values.put(COL_ENABLED, String.valueOf(alarm.isEnabled()));
        dbWrite.insertOrThrow(TABLE_NAME, null, values);
    }

    public List<RepeatableAlarm> restoreUiAlarms() {
        dbRead = dbHelper.getReadableDatabase();
        Cursor cursor = dbRead.query(
                TABLE_NAME,
                new String[] {COL_HOUR, COL_MINUTE, COL_DAYS, COL_ENABLED},
                null, null, null, null,
                COL_HOUR + ", " + COL_MINUTE);

        cursor.moveToFirst();

        List<RepeatableAlarm> result = new ArrayList<>();

        while(!cursor.isAfterLast()) {
            int hour = cursor.getInt(cursor.getColumnIndex(COL_HOUR));
            int minute = cursor.getInt(cursor.getColumnIndex(COL_MINUTE));
            int[] days = stringToDays(cursor.getString(cursor.getColumnIndex(COL_DAYS)));
            boolean isEnabled = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(COL_ENABLED)));

            result.add(new RepeatableAlarm(Time.of(hour, minute), days, isEnabled));

            cursor.moveToNext();
        }

        return result;
    }

    private String daysToString(int[] days) {
        String result = "";

        for(int i = 0; i < days.length; i++) {
            result += days[i];
            if (i < days.length - 1) {
                result += ",";
            }
        }

        if (result.isEmpty()) {
            result = "0";
        }

        return result;
    }

    private int[] stringToDays(String days) {
        String[] numbers = days.split(",");
        int[] result = new int[numbers.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(numbers[i]);
        }

        return result;
    }

}
