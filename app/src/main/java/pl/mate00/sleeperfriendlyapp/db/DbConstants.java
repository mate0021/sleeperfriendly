package pl.mate00.sleeperfriendlyapp.db;

/**
 * Created by mate00 on 01.02.15.
 */
public final class DbConstants {


    public static final int DATABASE_VERSION = 1;

    public static abstract class Timeline {
        public static final String DB_FILE_TIMELINE = "sleeperfriendly.db";

        public static final String TABLE_NAME = "timeline";
        public static final String COL_DAY_OF_WEEK = "dow";
        public static final String COL_HOUR = "hour";
        public static final String COL_MINUTE = "minute";
        public static final String COL_ENABLED = "enabled";

        public static final String COL_ALARM_COUNT = "alarm_count";
        public static final String ALARM_COUNT = "select count(*) as " + COL_ALARM_COUNT + " from " + TABLE_NAME;
        public static final String ALL_ALARMS = "select * from " + TABLE_NAME;
    }

    public static abstract class UiAlarm {
        public static final String DB_FILE_UI = "ui_alarms.db";

        public static final String TABLE_NAME = "ui_alarm";
        public static final String COL_HOUR = "hour";
        public static final String COL_MINUTE = "minute";
        public static final String COL_DAYS = "days";
        public static final String COL_ENABLED = "enabled";
    }

    public static abstract class TrackPath {
        public static final String DB_FILE_TRACKS = "mp3_tracks.db";

        public static final String TABLE_NAME = "played_mp3";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_PLAYED = "played";
    }
}
