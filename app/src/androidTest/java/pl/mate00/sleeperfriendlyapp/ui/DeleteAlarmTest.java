package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenu;
import org.robolectric.fakes.RoboMenuItem;

import pl.mate00.sleeperfriendlyapp.BuildConfig;
import pl.mate00.sleeperfriendlyapp.R;

import static android.app.Activity.RESULT_OK;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by mamy on 25.02.15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DeleteAlarmTest {

    private AlarmsListActivity alarmsListActivity;

    @Before
    public void setUp() {
        alarmsListActivity = buildActivity(AlarmsListActivity.class).create().get();
    }

    @Test
    public void addOneAlarm_DeleteOneAlarm_ListShouldBeEmpty() {
        addOneAlarm_WholeWeek();

        ListView alarmsList = (ListView) alarmsListActivity.findViewById(R.id.alarms_list);
        alarmsListActivity.onDeleteAlarmClick(getClickedMenuItem(alarmsList));

        assertEquals(0, alarmsList.getAdapter().getCount());
    }

    private void addOneAlarm_WholeWeek() {
        alarmsListActivity.onAddAlarmClick(null);

        Intent resultingIntent = new Intent();
        resultingIntent.putExtra(AlarmsListActivity.ALARM_RESULT_HOUR, 8);
        resultingIntent.putExtra(AlarmsListActivity.ALARM_RESULT_MINUTE, 15);
        resultingIntent.putExtra(AlarmsListActivity.ALARM_RESULT_DAYS, new int[] {1, 2, 3, 4, 5, 6, 7});

        shadowOf(alarmsListActivity).receiveResult(
                new Intent(alarmsListActivity, SetAlarmDetails.class),
                RESULT_OK,
                resultingIntent);
    }

    private MenuItem getClickedMenuItem(final View view) {
        MenuItem result = new RoboMenuItem() {

            public int getItemId() {
                return R.id.menu_item_delete_alarm;
            }

            public AdapterView.AdapterContextMenuInfo getMenuInfo() {
                return new AdapterView.AdapterContextMenuInfo(view, 0, 0);
            }
        };

        return result;
    }
}
