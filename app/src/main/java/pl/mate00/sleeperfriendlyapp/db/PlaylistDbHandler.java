package pl.mate00.sleeperfriendlyapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaylistDbHandler extends SQLiteOpenHelper {

    private static final String TAG = PlaylistDbHandler.class.getSimpleName();

    public static final String DB_NAME = "sleeper_friendly.db";

    public static final int VERSION = 1;

    public static final String TABLE_NAME = "played_mp3";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_PATH = "path";

    public static final String COLUMN_PLAYED = "played";

    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PATH + " TEXT NOT NULL, " +
            COLUMN_PLAYED + " INTEGER NOT NULL " +
            ");";

    public PlaylistDbHandler(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
