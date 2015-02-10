package pl.mate00.sleeperfriendlyapp.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;

public class AlarmsListActivity extends Activity { // ActionBarActivity {

    private ListView alarmsList;
    private ArrayAdapter<RepeatableAlarm> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_list_main_screen);

        RepeatableAlarm[] alarms = new RepeatableAlarm[] {
                new RepeatableAlarm(Time.of(8, 0), new int[] {1, 2, 3, 4, 5}),
                new RepeatableAlarm(Time.of(9, 0), new int[] {6, 7})
        };
//        adapter = new ArrayAdapter<RepeatableAlarm>(this, R.layout.alarm_list_row, alarms);
        adapter = new ArrayAdapter<RepeatableAlarm>(this, android.R.layout.simple_list_item_1, alarms);
        alarmsList = (ListView) findViewById(R.id.alarms_list);
        alarmsList.setAdapter(adapter);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_alarms_list, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
