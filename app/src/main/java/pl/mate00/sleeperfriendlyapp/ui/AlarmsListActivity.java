package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    private AddAlarmProxy proxy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_list_main_screen);

        uiAlarms = restoreListOfAlarms();
        adapter = new AlarmItemAdapter(this, R.layout.alarm_list_row, uiAlarms);
        alarmsList = (ListView) findViewById(R.id.alarms_list);
        alarmsList.setAdapter(adapter);

        proxy = new AddAlarmProxy(this);
        proxy.setUiListener(this);
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

            proxy.addRepeatableAlarm(hour, minute, days);
        }
    }

    private List<RepeatableAlarm> restoreListOfAlarms() {
        List<RepeatableAlarm> alarms = new ArrayList<RepeatableAlarm>();

//        alarms.add(new RepeatableAlarm(Time.of(8, 0), new int[] {1, 2, 3, 4, 5}));
//        alarms.add(new RepeatableAlarm(Time.of(9, 0), new int[] {6, 7}));

        return alarms;
    }

    @Override
    public void updateUiWithAlarm(RepeatableAlarm alarm) {
        uiAlarms.add(alarm);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorAfterAdding(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
