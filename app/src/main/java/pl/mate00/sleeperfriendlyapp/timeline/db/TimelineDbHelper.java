package pl.mate00.sleeperfriendlyapp.timeline.db;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.Timeline.*;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by mamy on 30.01.15.
 */
public class TimelineDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE =
            "create table " + TABLE_NAME + "(" +
                    COL_DAY_OF_WEEK + " integer," +
                    COL_HOUR + " integer," +
                    COL_MINUTE + " integer," +
                    COL_ENABLED + " integer," +
                    " primary key (" + COL_DAY_OF_WEEK + "," + COL_HOUR + "," + COL_MINUTE + ")" +
                    ");";

    public TimelineDbHelper(Context context) {
        this(context, DB_FILE, null, DATABASE_VERSION);
    }

    public TimelineDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void main(String[] args) {
        System.out.println(SQL_CREATE);
    }
}
