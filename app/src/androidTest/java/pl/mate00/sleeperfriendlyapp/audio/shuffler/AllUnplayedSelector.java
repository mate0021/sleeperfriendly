package pl.mate00.sleeperfriendlyapp.audio.shuffler;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.audio.shuffler.IPlaylistSelector;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.PathEntity;

public class AllUnplayedSelector implements IPlaylistSelector {
	
	public List<PathEntity> getAll() {
		List<PathEntity> result = new ArrayList<PathEntity>();
		
		result.add(new PathEntity(new Mp3Location("", "path1"), false));
		result.add(new PathEntity(new Mp3Location("", "path2"), false));
		result.add(new PathEntity(new Mp3Location("", "path3"), false));
        result.add(new PathEntity(new Mp3Location("", "path4"), false));
		
		return result;
	}

	@Override
	public void updateWithPath(PathEntity path) {
		// TODO Auto-generated method stub
		
	}

}
