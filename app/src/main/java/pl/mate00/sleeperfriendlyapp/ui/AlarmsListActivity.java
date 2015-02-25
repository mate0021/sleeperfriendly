package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;

public class AlarmsListActivity extends ActionBarActivity implements AddAlarmUiCase {

    static final int ALARM_TIME_REQUEST = 1;
    static final String ALARM_RESULT_HOUR = "ui_hour";
    static final String ALARM_RESULT_MINUTE = "ui_minute";
    static final String ALARM_RESULT_DAYS = "ui_days";

    private ListView alarmsList;
    private ArrayAdapter<RepeatableAlarm> adapter;
    private List<RepeatableAlarm> uiAlarms = new ArrayList<>();

    private AddAlarmProxy uiHandler;
    private UiAlarmState uiListHandler;

    private AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            RepeatableAlarm item = (RepeatableAlarm) parent.getItemAtPosition(position);
            Toast.makeText(AlarmsListActivity.this, item.toString(), Toast.LENGTH_LONG).show();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_list_main_screen);
        uiHandler = new AddAlarmProxy(this);
        uiHandler.setUiListener(this);
        uiListHandler = new UiAlarmState(this);

        uiAlarms = restoreListOfAlarms();
        adapter = new AlarmItemAdapter(this, R.layout.alarm_list_row, uiAlarms);

        alarmsList = (ListView) findViewById(R.id.alarms_list);
        alarmsList.setAdapter(adapter);
        alarmsList.setOnItemLongClickListener(itemLongClickListener);
    }

    public void addNewAlarm(View view) {
        Intent intent = new Intent(this, SetAlarmDetails.class);
        startActivityForResult(intent, ALARM_TIME_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALARM_TIME_REQUEST && resultCode == RESULT_OK) {
            int hour = data.getIntExtra(ALARM_RESULT_HOUR, 12);
            int minute = data.getIntExtra(ALARM_RESULT_MINUTE, 00);
            int[] days = data.getIntArrayExtra(ALARM_RESULT_DAYS);

            DateTime current = new DateTime();
            uiHandler.addRepeatableAlarm(hour, minute, days, current);
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
}
