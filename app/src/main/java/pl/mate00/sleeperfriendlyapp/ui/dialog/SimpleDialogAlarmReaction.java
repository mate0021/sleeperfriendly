package pl.mate00.sleeperfriendlyapp.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pl.mate00.sleeperfriendlyapp.ui.AlarmReaction;

/**
 * Created by mamy on 09.04.15.
 */
public class SimpleDialogAlarmReaction implements AlarmReaction {

    private static final String TAG = SimpleDialogAlarmReaction.class.getSimpleName();

    public SimpleDialogAlarmReaction() {}

    @Override
    public void onAlarmFired(Context context) {
        Log.d(TAG, " [][][] AlarmReaction called");
        Intent intent = new Intent(context, DialogLauncher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
