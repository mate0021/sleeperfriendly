package pl.mate00.sleeperfriendlyapp.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

public class Mp3PlayerMobile implements Mp3Player, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private static final String TAG = Mp3PlayerMobile.class.getSimpleName();

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    private static final String MP3_TRACK = SDCARD_PATH + "/mp3/miami.mp3";

    private MediaPlayer mediaPlayer;

    private Context context;

    public Mp3PlayerMobile() {
    }

    public Mp3PlayerMobile(Context context) {
        System.out.println("[][][] Mp3Constructor ");
        this.context = context;
    }

    @Override
    public void play() {
        Log.d(TAG, "[][][] Playing some stuff");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(MP3_TRACK);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play(String track) {
    }

    @Override
    public void stop() {
        Log.d(TAG, "[][][] Stopping playback");
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "[][][] alreadyPrepared");
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "[][][] playback completed");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        System.out.println("[][][] mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
        return false;
    }
}
