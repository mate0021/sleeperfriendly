package pl.mate00.sleeperfriendlyapp.audio.shuffler;

import java.util.List;

public interface IPlaylistSelector {
	
	List<PathEntity> getAll();
	
	void updateWithPath(PathEntity path);

}
