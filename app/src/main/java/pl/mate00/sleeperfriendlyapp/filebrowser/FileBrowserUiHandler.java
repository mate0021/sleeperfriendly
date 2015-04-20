package pl.mate00.sleeperfriendlyapp.filebrowser;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.audio.db.PlaylistSelector;
import pl.mate00.sleeperfriendlyapp.filebrowser.ui.FileBrowserCallback;

public class FileBrowserUiHandler {

    private Context context;

    private FileBrowserCallback callback;

    private PlaylistSelector playlistSelector;

    public FileBrowserUiHandler(Context context, FileBrowserCallback callback) {
        this.context = context;
        this.callback = callback;
        playlistSelector = new PlaylistSelector(context);
    }

    public void onItemMarked(SelectableItem item) {
        item.setContext(context);
        item.setFileBrowserCallback(callback);
        item.onTap();
    }

    public List<SelectableItem> getItemsForDirectory(String directory) {

        SelectableItem i1 = new DirectoryItem("/dir1/");
        SelectableItem i2 = new DirectoryItem("/dir2/");
        SelectableItem i3 = new FileItem("file1.mp3", false);
        SelectableItem i4 = new FileItem("file2.mp3", false);
        SelectableItem i5 = new FileItem("file3.mp3", false);
        SelectableItem i6 = new FileItem("file4.mp3", false);

        return Arrays.asList(i1, i2, i3, i4, i5, i6);
    }

}
