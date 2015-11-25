package pl.mate00.sleeperfriendlyapp.ui.dialog;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.audio.Mp3Player;
import pl.mate00.sleeperfriendlyapp.audio.Mp3ServiceLauncher;
import pl.mate00.sleeperfriendlyapp.audio.shuffler.Mp3Shuffler;
import pl.mate00.sleeperfriendlyapp.db.PlaylistSelector;
import pl.mate00.sleeperfriendlyapp.ui.AlarmReactionListener;

import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

public class DialogLauncher extends FragmentActivity implements AlarmReactionListener {

    private static final String TAG = DialogLauncher.class.getSimpleName();

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    private static final String MP3_TRACK = SDCARD_PATH + "/mp3/miami.mp3";

    private Mp3Player player = new Mp3ServiceLauncher(this);

    private Mp3Shuffler mp3Shuffler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, " [][][] showing DialogLauncher");

        setContentView(R.layout.activity_dialog_launcher);
        getWindow().addFlags(FLAG_SHOW_WHEN_LOCKED);
        DisplayAlarmDialogFragment alarmDialog = new DisplayAlarmDialogFragment();
        alarmDialog.show(getFragmentManager(), TAG);

        initShuffler();
        startPlayback();
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

    private void initShuffler() {
        mp3Shuffler = new Mp3Shuffler();
        mp3Shuffler.setSelector(new PlaylistSelector(this));
    }

    private void startPlayback() {
        String unplayedTrack = mp3Shuffler.getNext();
        player.play(unplayedTrack);
    }
}

