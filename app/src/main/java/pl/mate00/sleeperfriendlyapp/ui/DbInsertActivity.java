package pl.mate00.sleeperfriendlyapp.ui;

import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.timeline.Alarm;
import pl.mate00.sleeperfriendlyapp.timeline.Time;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;

public class DbInsertActivity extends ActionBarActivity {

    private static final String TAG = DbInsertActivity.class.getSimpleName();

    private Button btnAdd;

    private Timeline timeline;

    private View.OnClickListener addClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.d(TAG, "clicked");
            EditText txtDay = (EditText) findViewById(R.id.edit_day_of_week);
            EditText txtHour = (EditText) findViewById(R.id.edit_hour);
            EditText txtMinute = (EditText) findViewById(R.id.edit_minute);

            int day = Integer.parseInt(txtDay.getText().toString());
            int hour = Integer.parseInt(txtHour.getText().toString());
            int min = Integer.parseInt(txtMinute.getText().toString());

            timeline.addAlarm(new Alarm(Time.from(hour, min), day));
            
            Log.d(TAG, txtDay.getText().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeline = new TimelineDb(this);

        setContentView(R.layout.activity_db_insert);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(addClick);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_db_insert, menu);
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
