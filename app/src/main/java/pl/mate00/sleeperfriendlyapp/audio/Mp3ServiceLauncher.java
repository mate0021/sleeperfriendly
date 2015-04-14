package pl.mate00.sleeperfriendlyapp.audio;

import android.content.Context;
import android.content.Intent;

/**
 * Created by mamy on 14.04.15.
 */
public class Mp3ServiceLauncher implements Mp3Player {

    static final String INTENT_COMMAND = "pl.sleeperfriendlyapp.audio.CMD";
    static final String INTENT_COMMAND_START = "pl.sleeperfriendlyapp.audio.START";
    static final String INTENT_COMMAND_STOP = "pl.sleeperfriendlyapp.audio.STOP";

    private Context context;

    public Mp3ServiceLauncher(Context context) {
        this.context = context;
    }

    @Override
    public void play() {
        Intent playIntent = new Intent(context, Mp3PlayerService.class);
        playIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_START);
        context.startService(playIntent);
    }

    @Override
    public void play(String track) {

    }

    @Override
    public void stop() {
        Intent stopIntent = new Intent(context, Mp3PlayerService.class);
        stopIntent.putExtra(INTENT_COMMAND, INTENT_COMMAND_STOP);
        context.startService(stopIntent);
    }
}
