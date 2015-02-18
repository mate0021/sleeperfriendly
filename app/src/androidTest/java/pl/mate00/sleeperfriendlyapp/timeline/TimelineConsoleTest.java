package pl.mate00.sleeperfriendlyapp.timeline;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by mamy on 15.01.15.
 */
public class TimelineConsoleTest extends AbstractTimelineSuite {

    @Override
    public Timeline getTimelineInstance() {
        return new TimelineConsole();
    }

    @Test
    public void shouldAddOnlyUniqueAlarms() {
        subject.addAlarm(anyAlarm());
        subject.addAlarm(anyAlarm());

        assertEquals(1, subject.getNumberOfAlarms());
    }
}
