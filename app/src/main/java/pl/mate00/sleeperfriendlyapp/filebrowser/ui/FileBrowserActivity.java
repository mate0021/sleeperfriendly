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
        handler = new FileBrowserUiHandler(this, this);
        items = handler.getItemsForDirectory("");
        adapter = new FileItemAdapter(this, R.layout.file_browser_row, items);
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(this);
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
        updateUiFileListForDirectory("");
    }

    @Override
    public void onFileRemoved() {
        System.out.println("yes, file was removed");
        updateUiFileListForDirectory("");
    }

    @Override
    public void onDirectoryChanged() {}

    private void updateUiFileListForDirectory(String directory) {
        items = handler.getItemsForDirectory(directory);
        adapter.notifyDataSetChanged();
    }
}
