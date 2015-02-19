package pl.mate00.sleeperfriendlyapp.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

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
    public void test() {

    }
}
