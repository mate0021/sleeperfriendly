package pl.mate00.sleeperfriendlyapp.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.audio.Mp3Player;
import pl.mate00.sleeperfriendlyapp.audio.Mp3PlayerMobile;
import pl.mate00.sleeperfriendlyapp.ui.AlarmReactionListener;

import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public class DialogLauncher extends FragmentActivity implements AlarmReactionListener {

    private static final String TAG = DialogLauncher.class.getSimpleName();

    private Mp3Player player = Mp3PlayerMobile.getInstance(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "[][][] onCreate DialogLauncher");
        setContentView(R.layout.activity_dialog_launcher);
        getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);
        DisplayAlarmDialogFragment alarmDialog = new DisplayAlarmDialogFragment();
        alarmDialog.show(getFragmentManager(), TAG);

        player.play();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "[][][] onStart DialogLauncher");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d(TAG, "[][][] onAttach DialogLauncher");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "[][][] onPostResume DialogLauncher");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "[][][] onResume DialogLauncher");
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

