package pl.mate00.sleeperfriendlyapp.timeline.db;

import android.content.Context;

import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import pl.mate00.sleeperfriendlyapp.timeline.AbstractTimelineSuite;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class TimelineDbTest extends AbstractTimelineSuite {

    private Context context = Robolectric.application;

    @Override
    public Timeline getTimelineInstance() {
        return new TimelineDb(context);
    }

}