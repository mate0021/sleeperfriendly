package pl.mate00.sleeperfriendlyapp.audio.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.COLUMN_ID;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.COLUMN_PATH;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.COLUMN_PLAYED;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.DB_FILE_TRACKS;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.TABLE_NAME;

public class PlaylistDbHandler extends SQLiteOpenHelper {

    private static final String TAG = PlaylistDbHandler.class.getSimpleName();

    public static final int VERSION = 1;

    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PATH + " TEXT NOT NULL, " +
            COLUMN_PLAYED + " INTEGER NOT NULL " +
            ");";

    public PlaylistDbHandler(Context context) {
        super(context, DB_FILE_TRACKS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
