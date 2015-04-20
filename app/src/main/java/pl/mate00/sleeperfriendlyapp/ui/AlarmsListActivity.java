package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.Mp3Location;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.PathEntity;
import pl.mate00.sleeperfriendlyapp.audio.db.PlaylistSelector;
import pl.mate00.sleeperfriendlyapp.filebrowser.ui.FileBrowserActivity;

public class AlarmsListActivity extends ActionBarActivity implements UiCallbacks, AdapterView.OnItemClickListener {

    private static final String TAG = AlarmsListActivity.class.getSimpleName();

    static final int ALARM_TIME_REQUEST = 1;
    static final String ALARM_RESULT_HOUR = "ui_hour";
    static final String ALARM_RESULT_MINUTE = "ui_minute";
    static final String ALARM_RESULT_DAYS = "ui_days";

    private ListView alarmsList;
    private ArrayAdapter<RepeatableAlarm> adapter;
    private List<RepeatableAlarm> uiAlarms = new ArrayList<>();

    private UiAlarmState uiListHandler;

    private UiCasesHandler uiCasesHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_list_main_screen);

        uiCasesHandler = new UiCasesHandler(this);
        uiCasesHandler.setUiListener(this);

        uiListHandler = new UiAlarmState(this);

        uiAlarms = restoreListOfAlarms();
        adapter = new AlarmItemAdapter(this, R.layout.alarm_list_row, uiAlarms);

        alarmsList = (ListView) findViewById(R.id.alarms_list);
        alarmsList.setAdapter(adapter);
        alarmsList.setClickable(true);
        alarmsList.setOnItemClickListener(this);

        registerForContextMenu(alarmsList);
        resetPaths();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,  View view,  ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_alarm_item, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_alarms_list, menu);

        return true;
    }

    public void onAddAlarmClick(View view) {
        Intent intent = new Intent(this, SetAlarmDetails.class);
        startActivityForResult(intent, ALARM_TIME_REQUEST);
    }

    private void deleteAlarm(RepeatableAlarm alarm) {
        DateTime current = new DateTime();
        uiCasesHandler.deleteAlarm(alarm, current);
    }

    public void onDeleteAlarmClick(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        RepeatableAlarm selectedAlarm = uiAlarms.get(info.position);
        deleteAlarm(selectedAlarm);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALARM_TIME_REQUEST && resultCode == RESULT_OK) {
            int hour = data.getIntExtra(ALARM_RESULT_HOUR, 12);
            int minute = data.getIntExtra(ALARM_RESULT_MINUTE, 00);
            int[] days = data.getIntArrayExtra(ALARM_RESULT_DAYS);

            DateTime current = new DateTime();
            uiCasesHandler.addAlarm(hour, minute, days, current);
        }
    }

    private List<RepeatableAlarm> restoreListOfAlarms() {
        return uiListHandler.restoreUiAlarms();
    }

    @Override
    public void updateUiWithAlarm(RepeatableAlarm alarm) {
        uiAlarms.add(alarm);
        uiListHandler.addUiAlarm(alarm);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorAfterAdding(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateUiAfterDelete(RepeatableAlarm alarm) {
        uiAlarms.remove(alarm);
        uiListHandler.deleteUiAlarm(alarm);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorAfterDelete(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdateAfterSwitching(RepeatableAlarm alarm) {
        uiListHandler.deleteUiAlarm(alarm);
        uiListHandler.addUiAlarm(alarm);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_files:
                showFileBrowser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFileBrowser() {
        Intent intent = new Intent(this, FileBrowserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RepeatableAlarm alarm = adapter.getItem(position);
        CheckBox cbIsEnabled = (CheckBox) view.findViewById(R.id.is_enabled);
        boolean isEnabled = !cbIsEnabled.isChecked();
        DateTime current = new DateTime();
        uiCasesHandler.setAlarmEnabledTo(isEnabled, alarm, current);
        cbIsEnabled.setChecked(isEnabled);
    }

    private void resetPaths() {
        PlaylistSelector selector = new PlaylistSelector(this);
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "accid.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "dinner.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "f3.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "funk.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "ktowie.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "kuchwa.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "miami.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "monster.mp3"), false));
        selector.insertPath(new PathEntity(new Mp3Location("/sdcard/mp3/", "spies.mp3"), false));
    }

}
