package pl.mate00.sleeperfriendlyapp.filebrowser;

import android.content.Context;

import pl.mate00.sleeperfriendlyapp.filebrowser.ui.FileBrowserCallback;

public class FileBrowserUiHandler {

    private Context context;

    private FileBrowserCallback callback;

    public FileBrowserUiHandler(Context context) {
        this.context = context;
    }

    public void onItemMarked(SelectableItem item) {
        item.setContext(context);
        item.setFileBrowserCallback(callback);
        item.onTap();
    }

    public void setFileBrowserCallback(FileBrowserCallback callback) {
        this.callback = callback;
    }
}
