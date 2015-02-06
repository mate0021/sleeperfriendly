package pl.mate00.sleeperfriendlyapp.timeline.db;

import android.content.Context;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class TimelineDbTest  {

    private TimelineDb subject;
    private Context context = Robolectric.application;

    @Before
    public void setUp() {
        subject = new TimelineDb(context);
    }

    @Test
    public void timelineShouldBeEmpty() {
        System.out.println(subject.getNumberOfAlarms());
        assertTrue(subject.isEmpty());
    }

}