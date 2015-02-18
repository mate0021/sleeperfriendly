package pl.mate00.sleeperfriendlyapp.timeline.db;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.mate00.sleeperfriendlyapp.timeline.AbstractTimelineSuite;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

import static junit.framework.Assert.assertEquals;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class TimelineDbTest extends AbstractTimelineSuite {

    private Context context = Robolectric.application;

    @Override
    public Timeline getTimelineInstance() {
        return new TimelineDb(context);
    }

    @Test(expected = SQLiteException.class)
    public void shouldAddOnlyUniqueAlarms() {
        subject.addAlarm(anyAlarm());
        subject.addAlarm(anyAlarm());

        assertEquals(1, subject.getNumberOfAlarms());
    }
}