package pl.mate00.sleeperfriendlyapp.timeline;

/**
 * Created by mamy on 15.01.15.
 */
public class TimelineConsoleTest extends AbstractTimelineSuite {

    @Override
    public Timeline getTimelineInstance() {
        return new TimelineConsole();
    }

}
