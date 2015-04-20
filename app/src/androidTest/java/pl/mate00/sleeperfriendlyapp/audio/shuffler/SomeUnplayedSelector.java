package pl.mate00.sleeperfriendlyapp.audio.shuffler;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.audio.shuffler.IPlaylistSelector;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.PathEntity;

public class SomeUnplayedSelector implements IPlaylistSelector {
	
	public List<PathEntity> getAll() {
		List<PathEntity> result = new ArrayList<PathEntity>();
		
		result.add(new PathEntity(new Mp3Location("path1", "track1"), false));
		result.add(new PathEntity(new Mp3Location("path2", "track2"), true));
		result.add(new PathEntity(new Mp3Location("path3", "track3"), false));
        result.add(new PathEntity(new Mp3Location("path4", "track4"), false));
		
		return result;
	}

	@Override
	public void updateWithPath(PathEntity path) {
		// TODO Auto-generated method stub
		
	}

}
