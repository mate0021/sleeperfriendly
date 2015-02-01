package pl.mate00.sleeperfriendlyapp.db;

/**
 * Created by mate00 on 01.02.15.
 */
public final class DbConstants {

    public static final String DB_FILE = "sleeperfriendly.db";

    public static abstract class Timeline {
        public static final String TABLE_NAME = "timeline";
        public static final String COL_DAY_OF_WEEK = "dow";
        public static final String COL_HOUR = "hour";
        public static final String COL_MINUTE = "minute";
        public static final String COL_ENABLED = "enabled";

//        public static final String INSERT_ALARM = "insert into " + TABLE_NAME + " values (?, ?, ?, ?);";
    }
}
