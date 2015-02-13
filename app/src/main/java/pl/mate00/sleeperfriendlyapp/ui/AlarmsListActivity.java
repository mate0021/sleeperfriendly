package pl.mate00.sleeperfriendlyapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

public class AlarmsListActivity extends ActionBarActivity {

    static final int ALARM_TIME_REQUEST = 1;
    static final String ALARM_RESULT_HOUR = "ui_hour";
    static final String ALARM_RESULT_MINUTE = "ui_minute";
    static final String ALARM_RESULT_DAYS = "ui_days";

    private ListView alarmsList;
    private ArrayAdapter<RepeatableAlarm> adapter;
    private List<RepeatableAlarm> uiAlarms = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("main onCreate");
        setContentView(R.layout.alarms_list_main_screen);

        uiAlarms = restoreListOfAlarms();
        adapter = new AlarmItemAdapter(this, R.layout.alarm_list_row, uiAlarms);
        alarmsList = (ListView) findViewById(R.id.alarms_list);
        alarmsList.setAdapter(adapter);
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

            updateUiWithAlarm(hour, minute, days);
        }
    }

    private void updateUiWithAlarm(int hour, int minute, int[] days) {
        if (days.length == 0) {
            DateTime currentTime = new DateTime();
            uiAlarms.add(new RepeatableAlarm(Time.of(hour, minute), currentTime));
        } else {
            uiAlarms.add(new RepeatableAlarm(Time.of(hour, minute), days));
        }
        adapter.notifyDataSetChanged();
    }

    private List<RepeatableAlarm> restoreListOfAlarms() {
        List<RepeatableAlarm> alarms = new ArrayList<RepeatableAlarm>();

        alarms.add(new RepeatableAlarm(Time.of(8, 0), new int[] {1, 2, 3, 4, 5}));
        alarms.add(new RepeatableAlarm(Time.of(9, 0), new int[] {6, 7}));

        return alarms;
    }
}
