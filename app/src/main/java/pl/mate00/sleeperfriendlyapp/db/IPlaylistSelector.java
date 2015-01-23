package pl.mate00.sleeperfriendlyapp.db;

import java.util.List;

public interface IPlaylistSelector {
	
	List<PathEntity> getAll();
	
	void updateWithPath(PathEntity path);

}
