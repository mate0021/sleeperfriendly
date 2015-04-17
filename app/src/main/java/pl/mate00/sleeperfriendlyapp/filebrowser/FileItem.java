package pl.mate00.sleeperfriendlyapp.filebrowser;

import pl.mate00.sleeperfriendlyapp.audio.db.PlaylistSelector;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.PathEntity;

public class FileItem extends SelectableItem {

    private PlaylistSelector playlistSelector;

    public FileItem(String fileName, boolean isSelected) {
        super(fileName, isSelected);
    }

    @Override
    public void onTap() {
        playlistSelector = new PlaylistSelector(context);
        PathEntity path = new PathEntity(getFileName());
        if (isSelected()) {
            System.out.println("will remove " + path);
            playlistSelector.removePath(path);
            callback.onFileRemoved();
        } else {
            System.out.println("will add " + path);
            playlistSelector.insertPath(path);
            callback.onFileAdded();
        }
    }

    @Override
    public boolean isSelectedVisible() {
        return true;
    }
}
