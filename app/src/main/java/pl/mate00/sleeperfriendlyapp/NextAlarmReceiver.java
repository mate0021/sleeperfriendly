package pl.mate00.sleeperfriendlyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NextAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = NextAlarmReceiver.class.getSimpleName();

    public NextAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "alarm fired");
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
    }
}
