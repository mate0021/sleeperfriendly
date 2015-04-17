package pl.mate00.sleeperfriendlyapp.filebrowser.ui;

/**
 * Created by mamy on 17.04.15.
 */
public interface FileBrowserCallback {

    void onFileAdded();

    void onFileRemoved();

    void onDirectoryChanged();
}
