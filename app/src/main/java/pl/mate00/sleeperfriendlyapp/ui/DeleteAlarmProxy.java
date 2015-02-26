package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Context;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;
import pl.mate00.sleeperfriendlyapp.timeline.Timeline;
import pl.mate00.sleeperfriendlyapp.timeline.db.TimelineDb;

/**
 * Created by mamy on 26.02.15.
 */
public class DeleteAlarmProxy {

    private Context context;

    private DeleteAlarmUiCase uiListener;

    private Timeline timeline;

    public DeleteAlarmProxy(Context context) {
        this.context = context;
        this.timeline = new TimelineDb(context);
    }

    public void setUiListener(DeleteAlarmUiCase uiListener) {
        this.uiListener = uiListener;
    }

    public void deleteAlarm(RepeatableAlarm alarm) {
    }
}
