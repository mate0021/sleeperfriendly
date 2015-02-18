package pl.mate00.sleeperfriendlyapp.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.DATABASE_VERSION;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_DAYS;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_ENABLED;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_HOUR;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.COL_MINUTE;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.DB_FILE_UI;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.TABLE_NAME;

/**
 * Created by mamy on 17.02.15.
 */
public class UiAlarmDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE =
            "create table " + TABLE_NAME + "(" +
                    COL_HOUR + " integer," +
                    COL_MINUTE + " integer," +
                    COL_DAYS + " text, " +
                    COL_ENABLED + " text, " +
                    " primary key (" + COL_HOUR + "," + COL_MINUTE + ", " + COL_DAYS + ")" +
                    ");";

    public UiAlarmDbHelper(Context context) {
        this(context, DB_FILE_UI, null, DATABASE_VERSION);
    }

    public UiAlarmDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
