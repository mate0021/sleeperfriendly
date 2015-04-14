package pl.mate00.sleeperfriendlyapp.audio;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;

import java.io.IOException;

public class Mp3PlayerService extends Service implements MediaPlayer.OnPreparedListener {

    private static final String TAG = Mp3PlayerService.class.getSimpleName();

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    private static final String MP3_TRACK = SDCARD_PATH + "/mp3/miami.mp3";

    private MediaPlayer mediaPlayer;

    public Mp3PlayerService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String cmd = intent.getStringExtra(Mp3ServiceLauncher.INTENT_COMMAND);

        if (cmd.equals(Mp3ServiceLauncher.INTENT_COMMAND_START)) {
            startPlayback();
        }

        if (cmd.equals(Mp3ServiceLauncher.INTENT_COMMAND_STOP)) {
            stopPlayback();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void startPlayback() {
        if (mediaPlayer.isPlaying()) {
            return;
        }
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(MP3_TRACK);
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlayback() {
        mediaPlayer.stop();
        mediaPlayer.release();
        stopSelf();
    }

}
