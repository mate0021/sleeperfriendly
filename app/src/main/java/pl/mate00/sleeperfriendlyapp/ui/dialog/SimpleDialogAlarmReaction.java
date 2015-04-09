package pl.mate00.sleeperfriendlyapp.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import pl.mate00.sleeperfriendlyapp.ui.AlarmReaction;

/**
 * Created by mamy on 09.04.15.
 */
public class SimpleDialogAlarmReaction implements AlarmReaction {

    public SimpleDialogAlarmReaction() {}

    @Override
    public void onAlarmFired(Context context) {
        Intent intent = new Intent(context, DialogLauncher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
