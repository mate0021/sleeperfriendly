package pl.mate00.sleeperfriendlyapp.timeline.db;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import pl.mate00.sleeperfriendlyapp.BuildConfig;
import pl.mate00.sleeperfriendlyapp.timeline.AbstractTimelineSuite;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class TimelineDbTest extends AbstractTimelineSuite {

    private Context context = RuntimeEnvironment.application;

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