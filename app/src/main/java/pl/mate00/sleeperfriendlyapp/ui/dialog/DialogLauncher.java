package pl.mate00.sleeperfriendlyapp.ui.dialog;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.audio.Mp3Player;
import pl.mate00.sleeperfriendlyapp.audio.Mp3ServiceLauncher;
import pl.mate00.sleeperfriendlyapp.ui.AlarmReactionListener;

import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public class DialogLauncher extends FragmentActivity implements AlarmReactionListener {

    private static final String TAG = DialogLauncher.class.getSimpleName();

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    private static final String MP3_TRACK = SDCARD_PATH + "/mp3/miami.mp3";

    private Mp3Player player = new Mp3ServiceLauncher(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_launcher);
        getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);
        DisplayAlarmDialogFragment alarmDialog = new DisplayAlarmDialogFragment();
        alarmDialog.show(getFragmentManager(), TAG);

        player.play(MP3_TRACK);
    }

    @Override
    public void onContinueAlarm() {
        finish();

        player.stop();
    }

    @Override
    public void onTerminateAlarm() {
        finish();

        player.stop();
    }
}

