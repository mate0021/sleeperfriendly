package pl.mate00.sleeperfriendlyapp.filebrowser;

import pl.mate00.sleeperfriendlyapp.filebrowser.SelectableItem;

public class DirectoryItem extends SelectableItem {

    public DirectoryItem(String fileName) {
        super(fileName);
    }

    @Override
    public void onTap() {}

    @Override
    public boolean isSelectedVisible() {
        return false;
    }
}
