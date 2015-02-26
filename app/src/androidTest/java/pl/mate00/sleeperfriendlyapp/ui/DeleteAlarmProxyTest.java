package pl.mate00.sleeperfriendlyapp.ui;

import static org.mockito.Mockito.*;
import android.content.Context;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by mamy on 26.02.15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18, reportSdk = 18)
public class DeleteAlarmProxyTest {

    private DeleteAlarmProxy subject;

    private AddAlarmProxy addAlarmProxy;

    private DeleteAlarmUiCase uiListenerMock;

    @Before
    public void setUp() {
        Context context = Robolectric.application;
        subject = new DeleteAlarmProxy(context);
        uiListenerMock = mock(DeleteAlarmUiCase.class);
        subject.setUiListener(uiListenerMock);

        addAlarmProxy = new AddAlarmProxy(context);
    }

    @Test
    public void t() {
        addAlarm();
        subject.deleteAlarm(null);
    }

    private void addAlarm() {
        DateTime current = new DateTime().withDayOfWeek(1).withTime(0, 0, 0, 0);
        addAlarmProxy.addRepeatableAlarm(8, 15, new int[] {1, 2, 3, 4}, current);
    }
}