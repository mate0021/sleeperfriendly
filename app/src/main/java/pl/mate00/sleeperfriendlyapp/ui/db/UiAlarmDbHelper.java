package pl.mate00.sleeperfriendlyapp.ui.db;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.*;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.UiAlarm.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mamy on 17.02.15.
 */
public class UiAlarmDbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE =
            "create table " + TABLE_NAME + "(" +
                    COL_HOUR + " integer," +
                    COL_MINUTE + " integer," +
                    COL_DAYS + " text, " +
                    COL_ENABLED + " integer," +
                    " primary key (" + COL_HOUR + "," + COL_MINUTE + ", " + COL_DAYS + ")" +
                    ");";

    public UiAlarmDbHelper(Context context) {
        this(context, DB_FILE, null, DATABASE_VERSION);
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
