package pl.mate00.sleeperfriendlyapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import pl.mate00.sleeperfriendlyapp.db.IPlaylistSelector;
import pl.mate00.sleeperfriendlyapp.db.PathEntity;

public class Mp3Shuffler {

    public static final String EMPTY = "";

    private IPlaylistSelector selector;


	public void setSelector(IPlaylistSelector selector) {
		this.selector = selector;
	}
	
	public String getNext() {
		List<PathEntity> allPaths = selector.getAll();

        if (allPaths.isEmpty()) {
			return EMPTY;
		} else {
			return getRandomFromAllPlayed(allPaths);
		}
	}
	
	private List<PathEntity> filterUnplayedFrom(List<PathEntity> paths) {
		List<PathEntity> result = new ArrayList<PathEntity>();
		
		for (PathEntity path : paths) {
			if (!path.isPlayed()) {
				result.add(path);
			}
		}
		return result;
	}
	
	private String getRandomFromAllPlayed(List<PathEntity> allPaths) {
        List<PathEntity> filtered = filterUnplayedFrom(allPaths);
		if (filtered.isEmpty()) {
            for (PathEntity p : allPaths) {
                p.setPlayed(false);
                selector.updateWithPath(p);
            }
			return getRandomFromAllPlayed(allPaths);
		} else {
			Collections.shuffle(filtered);
			PathEntity randomPath = filtered.get(0);
			randomPath.setPlayed(true);
			selector.updateWithPath(randomPath);
			return randomPath.getPath();
		}
	}
}
