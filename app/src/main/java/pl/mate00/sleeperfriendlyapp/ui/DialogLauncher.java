package pl.mate00.sleeperfriendlyapp.ui;

import android.nfc.Tag;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pl.mate00.sleeperfriendlyapp.R;

public class DialogLauncher extends FragmentActivity implements AlarmReactionListener {

    private static final String TAG = DialogLauncher.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_launcher);
        DisplayAlarmDialogFragment alarmDialog = new DisplayAlarmDialogFragment();
        alarmDialog.show(getFragmentManager(), "some_tag");
    }

    @Override
    public void onContinueAlarm() {
        Log.d(TAG, "[][][] Alarm will be continued");
    }

    @Override
    public void onTerminateAlarm() {
        Log.d(TAG, "[][][] Alarm will be terminated");
    }
}
