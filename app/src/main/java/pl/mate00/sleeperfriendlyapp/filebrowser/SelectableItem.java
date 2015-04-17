package pl.mate00.sleeperfriendlyapp.filebrowser;

import android.content.Context;

import pl.mate00.sleeperfriendlyapp.audio.shuffler.PathEntity;
import pl.mate00.sleeperfriendlyapp.filebrowser.ui.FileBrowserCallback;

public abstract class SelectableItem {

    private boolean isSelected;

    private String fileName;

    protected Context context;

    protected FileBrowserCallback callback;

    public SelectableItem(String fileName) {
        this(fileName, false);
    }

    public SelectableItem(String fileName, boolean isSelected) {
        this.fileName = fileName;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setFileBrowserCallback(FileBrowserCallback callback) {
        this.callback = callback;
    }

    public abstract void onTap();

    public abstract boolean isSelectedVisible();
}
