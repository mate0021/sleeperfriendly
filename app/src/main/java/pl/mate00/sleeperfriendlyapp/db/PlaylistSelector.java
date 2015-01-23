package pl.mate00.sleeperfriendlyapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
        value.put(PlaylistDbHandler.COLUMN_PATH, path.getPath());
        value.put(PlaylistDbHandler.COLUMN_PLAYED, playedToCode(path.isPlayed()));
        db.insert(PlaylistDbHandler.TABLE_NAME, null, value);

        db.close();
        dbHandler.close();
    }
    
    @Override
    public List<PathEntity> getAll() {
    	db = dbHandler.getReadableDatabase();

        String[] columns = new String[] { PlaylistDbHandler.COLUMN_PATH, PlaylistDbHandler.COLUMN_PLAYED };

        Cursor c = db.query(PlaylistDbHandler.TABLE_NAME, columns, null, null, null, null, null);

        List<PathEntity> result = new ArrayList<PathEntity>();
        while (c.moveToNext()) {
            String path = c.getString(c.getColumnIndex(PlaylistDbHandler.COLUMN_PATH));
            int code = c.getInt(c.getColumnIndex(PlaylistDbHandler.COLUMN_PLAYED));
			result.add(new PathEntity(path, wasPlayed(code)));
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
		values.put(PlaylistDbHandler.COLUMN_PLAYED, playedToCode(path.isPlayed()));

		db.update(PlaylistDbHandler.TABLE_NAME, values,
				PlaylistDbHandler.COLUMN_PATH + " = ?", new String[] { path.getPath() });

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
