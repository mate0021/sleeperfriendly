package pl.mate00.sleeperfriendlyapp.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.audio.Mp3Player;
import pl.mate00.sleeperfriendlyapp.audio.Mp3PlayerMobile;
import pl.mate00.sleeperfriendlyapp.ui.AlarmReactionListener;

import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public class DialogLauncher extends FragmentActivity implements AlarmReactionListener {

    private static final String TAG = DialogLauncher.class.getSimpleName();

    private Mp3Player player = new Mp3PlayerMobile(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_launcher);
        getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);
        DisplayAlarmDialogFragment alarmDialog = new DisplayAlarmDialogFragment();
        alarmDialog.show(getFragmentManager(), TAG);

        player.play();
    }

    @Override
    public void onContinueAlarm() {
        Log.d(TAG, "[][][] Alarm will be continued");
        finish();

        player.stop();
    }

    @Override
    public void onTerminateAlarm() {
        Log.d(TAG, "[][][] Alarm will be terminated");
        finish();

        player.stop();
    }
}

