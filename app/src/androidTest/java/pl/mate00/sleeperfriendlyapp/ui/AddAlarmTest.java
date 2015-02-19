package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.mate00.sleeperfriendlyapp.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by mamy on 19.02.15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, reportSdk = 18)
public class AddAlarmTest {

    private AlarmsListActivity alarmsListActivity;

    @Before
    public void setUp() {
        alarmsListActivity = Robolectric.buildActivity(AlarmsListActivity.class).create().get();
    }

    @Test
    public void activityIsNotNull() {
        assertNotNull(alarmsListActivity);
    }

    @Test
    public void alarmsListIsNotNull() {
        ListView listView = (ListView) alarmsListActivity.findViewById(R.id.alarms_list);

        assertNotNull(listView);
    }

    @Test
    public void alarmsListIsEmpty() {
        ListView listView = (ListView) alarmsListActivity.findViewById(R.id.alarms_list);

        assertEquals(0, listView.getAdapter().getCount());
    }

    @Test
    public void addAlarmButtonIsClickable() {
        Button addAlarmButton = (Button) alarmsListActivity.findViewById(R.id.btn_new_alarm);

        assertTrue(addAlarmButton.isClickable());
    }

    @Test
    public void clickingAddButtonStartsSetAlarmDetailsActivity() {
        Button addAlarmButton = (Button) alarmsListActivity.findViewById(R.id.btn_new_alarm);

        addAlarmButton.performClick();

        Intent startedIntent = Robolectric.shadowOf(alarmsListActivity).getNextStartedActivity();

        assertEquals(startedIntent.getComponent().getClassName(), SetAlarmDetails.class.getName());
    }

    @Test
    public void backToAlarmsListAfterSettingAlarm_ListHasAlarm() {
        alarmsListActivity.addNewAlarm(null);

        Intent resultingIntent = new Intent();
        resultingIntent.putExtra(AlarmsListActivity.ALARM_RESULT_HOUR, "8");
        resultingIntent.putExtra(AlarmsListActivity.ALARM_RESULT_MINUTE, "0");
        resultingIntent.putExtra(AlarmsListActivity.ALARM_RESULT_DAYS, new int[] {});

        Robolectric.shadowOf(alarmsListActivity).receiveResult(
                new Intent(alarmsListActivity, SetAlarmDetails.class),
                RESULT_OK,
                resultingIntent);

        ListView listView = (ListView) alarmsListActivity.findViewById(R.id.alarms_list);
        assertEquals(1, listView.getAdapter().getCount());
    }

    @Test
    public void backToAlarmsListAfterCancellingSettingAlarm_ListEmpty() {
        alarmsListActivity.addNewAlarm(null);

        Robolectric.shadowOf(alarmsListActivity).receiveResult(
                new Intent(alarmsListActivity, SetAlarmDetails.class),
                RESULT_CANCELED,
                null
        );
        ListView listView = (ListView) alarmsListActivity.findViewById(R.id.alarms_list);
        assertEquals(0, listView.getAdapter().getCount());
    }

}
