package pl.mate00.sleeperfriendlyapp.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mamy on 08.04.15.
 */
public class ToastReaction implements AlarmReaction {

    @Override
    public void onAlarmFired(Context context) {
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
    }
}
