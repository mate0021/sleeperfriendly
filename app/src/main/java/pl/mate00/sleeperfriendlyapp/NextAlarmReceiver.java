package pl.mate00.sleeperfriendlyapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NextAlarmReceiver extends BroadcastReceiver {
    public NextAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        System.out.println(" *** intent.getAction()");
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
    }
}
