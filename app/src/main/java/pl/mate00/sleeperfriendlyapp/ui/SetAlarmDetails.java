package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.R;

public class SetAlarmDetails extends ActionBarActivity {

    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_details);

        timePicker = (TimePicker) findViewById(R.id.time_picker);
    }

    public void saveAlarm(View view) {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        int[] days = getDays();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(AlarmsListActivity.ALARM_RESULT_HOUR, hour);
        resultIntent.putExtra(AlarmsListActivity.ALARM_RESULT_MINUTE, minute);
        resultIntent.putExtra(AlarmsListActivity.ALARM_RESULT_DAYS, days);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private int[] getDays() {
        CheckBox cbMonday = (CheckBox) findViewById(R.id.monday);
        CheckBox cbTuesday = (CheckBox) findViewById(R.id.tuesday);
        CheckBox cbWednesday = (CheckBox) findViewById(R.id.wednesday);
        CheckBox cbThursday = (CheckBox) findViewById(R.id.thursday);
        CheckBox cbFriday = (CheckBox) findViewById(R.id.friday);
        CheckBox cbSaturday = (CheckBox) findViewById(R.id.saturday);
        CheckBox cbSunday = (CheckBox) findViewById(R.id.sunday);

        List<Integer> checkedDays = new ArrayList<>();
        if (cbMonday.isChecked()) {
            checkedDays.add(1);
        }
        if (cbTuesday.isChecked()) {
            checkedDays.add(2);
        }
        if (cbWednesday.isChecked()) {
            checkedDays.add(3);
        }
        if (cbThursday.isChecked()) {
            checkedDays.add(4);
        }
        if (cbFriday.isChecked()) {
            checkedDays.add(5);
        }
        if (cbSaturday.isChecked()) {
            checkedDays.add(6);
        }
        if (cbSunday.isChecked()) {
            checkedDays.add(7);
        }

        return Ints.toArray(checkedDays);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_alarm_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}