package pl.mate00.sleeperfriendlyapp.filebrowser;

import pl.mate00.sleeperfriendlyapp.filebrowser.SelectableItem;

public class FileItem extends SelectableItem {

    public FileItem(String fileName, boolean isSelected) {
        super(fileName, isSelected);
    }

    @Override
    public void onTap() {}

    @Override
    public boolean isSelectedVisible() {
        return true;
    }
}
