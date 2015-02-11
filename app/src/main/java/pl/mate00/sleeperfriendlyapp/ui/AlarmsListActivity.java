package pl.mate00.sleeperfriendlyapp.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

public class AlarmsListActivity extends ActionBarActivity {

    private ListView alarmsList;
    private Button btnAddAlarm;
    private ArrayAdapter<RepeatableAlarm> adapter;
    private View.OnClickListener addAlarmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_list_main_screen);

        List<RepeatableAlarm> alarms = new ArrayList<RepeatableAlarm>();
        alarms.add(new RepeatableAlarm(Time.of(8, 0), new int[] {1, 2, 3, 4, 5}));
        alarms.add(new RepeatableAlarm(Time.of(9, 0), new int[] {6, 7}));

        adapter = new AlarmItemAdapter(this, R.layout.alarm_list_row, alarms);
        alarmsList = (ListView) findViewById(R.id.alarms_list);
        alarmsList.setAdapter(adapter);

        btnAddAlarm = (Button) findViewById(R.id.btn_new_alarm);
        btnAddAlarm.setOnClickListener(addAlarmClickListener);
    }


}
