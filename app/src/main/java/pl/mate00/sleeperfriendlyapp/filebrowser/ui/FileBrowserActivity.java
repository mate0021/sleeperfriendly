package pl.mate00.sleeperfriendlyapp.filebrowser.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.filebrowser.DirectoryItem;
import pl.mate00.sleeperfriendlyapp.filebrowser.FileBrowserUiHandler;
import pl.mate00.sleeperfriendlyapp.filebrowser.FileItem;
import pl.mate00.sleeperfriendlyapp.filebrowser.SelectableItem;

public class FileBrowserActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,
        FileBrowserCallback {

    private ListView listView;

    private ArrayAdapter<SelectableItem> adapter;

    private List<SelectableItem> items;

    private FileBrowserUiHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);

        listView = (ListView) findViewById(R.id.list_file_items);
        adapter = new FileItemAdapter(this, R.layout.file_browser_row, getItems());
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(this);

        handler = new FileBrowserUiHandler(this);
        handler.setFileBrowserCallback(this);
    }

    private List<SelectableItem> getItems() {
        SelectableItem i1 = new DirectoryItem("/dir1/");
        SelectableItem i2 = new DirectoryItem("/dir2/");
        SelectableItem i3 = new FileItem("file1.mp3", false);
        SelectableItem i4 = new FileItem("file2.mp3", false);
        SelectableItem i5 = new FileItem("file3.mp3", false);
        SelectableItem i6 = new FileItem("file4.mp3", false);

        return Arrays.asList(i1, i2, i3, i4, i5, i6);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SelectableItem item = adapter.getItem(position);
        handler.onItemMarked(item);
        CheckBox cbIsSelected = (CheckBox) view.findViewById(R.id.is_file_selected);
        cbIsSelected.setChecked(!cbIsSelected.isChecked());
    }

    @Override
    public void onFileAdded() {
        System.out.println("yes, file was added");
    }

    @Override
    public void onFileRemoved() {
        System.out.println("yes, file was removed");
    }

    @Override
    public void onDirectoryChanged() {}
}
