package pl.mate00.sleeperfriendlyapp.audio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.audio.shuffler.IPlaylistSelector;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.Mp3Location;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.PathEntity;

import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.COLUMN_PATH;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.COLUMN_PLAYED;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.COLUMN_TRACK;
import static pl.mate00.sleeperfriendlyapp.db.DbConstants.TrackPath.TABLE_NAME;

public class PlaylistSelector implements IPlaylistSelector {

    private static final String TAG = PlaylistSelector.class.getSimpleName();

    private static final int NOT_PLAYED = 0;

    private static final int PLAYED = 1;

    private PlaylistDbHandler dbHandler;

    private SQLiteDatabase db;

    public PlaylistSelector(Context context) {
        dbHandler = new PlaylistDbHandler(context);
    }

    public void insertPath(PathEntity path) {
        db = dbHandler.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_PATH, path.getMp3Location().getPath());
        value.put(COLUMN_TRACK, path.getMp3Location().getTrack());
        value.put(COLUMN_PLAYED, playedToCode(path.isPlayed()));
        db.insert(TABLE_NAME, null, value);

        db.close();
        dbHandler.close();
    }

    public void removePath(PathEntity path) {
        db = dbHandler.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_PATH + " = ? AND " + COLUMN_TRACK + " = ? ",
                new String[]{ path.getMp3Location().getPath(), path.getMp3Location().getTrack() });
        db.close();
        dbHandler.close();
    }

    public List<PathEntity> getTracksForDirectory(String directory) {
        return Collections.EMPTY_LIST;
    }
    
    @Override
    public List<PathEntity> getAll() {
    	db = dbHandler.getReadableDatabase();

        String[] columns = new String[] { COLUMN_PATH, COLUMN_TRACK, COLUMN_PLAYED };

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        List<PathEntity> result = new ArrayList<PathEntity>();
        while (c.moveToNext()) {
            String path = c.getString(c.getColumnIndex(COLUMN_PATH));
            String track = c.getString(c.getColumnIndex(COLUMN_TRACK));
            int code = c.getInt(c.getColumnIndex(COLUMN_PLAYED));

			result.add(new PathEntity(new Mp3Location(path, track), wasPlayed(code)));
        }
        c.close();

        db.close();
        dbHandler.close();

        return result;
    }
    
    @Override
	public void updateWithPath(PathEntity path) {
		db = dbHandler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_PLAYED, playedToCode(path.isPlayed()));

		db.update(TABLE_NAME, values, COLUMN_PATH + " = ? AND " + COLUMN_TRACK + " = ? ",
                new String[] { path.getMp3Location().getPath(), path.getMp3Location().getTrack() });

		db.close();
		dbHandler.close();
	}

    // TODO: to enum
    private boolean wasPlayed(int code) {
        return code != NOT_PLAYED;
    }
    
    private int playedToCode(boolean wasPlayed) {
    	return wasPlayed ? PLAYED : NOT_PLAYED;
    }
}

/*
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/accid.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/dinner.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/f3.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/funk.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/ktowie.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/kuchwa.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/miami.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/monster.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/rush.mp3', 0);
insert into played_mp3(path, played) values ('/storage/sdcard/mp3/spies.mp3', 0);
 */