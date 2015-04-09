package pl.mate00.sleeperfriendlyapp.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import pl.mate00.sleeperfriendlyapp.ui.AlarmReactionListener;

import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.ON_AFTER_RELEASE;
import static android.os.PowerManager.SCREEN_DIM_WAKE_LOCK;

/**
 * Created by mamy on 08.04.15.
 */
public class DisplayAlarmDialogFragment extends DialogFragment {

    private static final String TAG = DisplayAlarmDialogFragment.class.getSimpleName();

    private AlarmReactionListener listener;

    private PowerManager.WakeLock wakeLock;

    private DialogInterface.OnClickListener positiveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.d(TAG, "positive click");
            listener.onTerminateAlarm();
        }
    };

    private DialogInterface.OnClickListener negativeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Log.d(TAG, "negative click");
            listener.onContinueAlarm();
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage("Message")
                .setPositiveButton("Positive", positiveClickListener)
                .setNegativeButton("Negative", negativeClickListener);

        aquireWakeLock();

        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "[][][] attaching");
        listener = (AlarmReactionListener) activity;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        Log.d(TAG, "[][][] dismissing");
        releaseWakeLock();
    }

    private void aquireWakeLock() {
        PowerManager powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(SCREEN_DIM_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, TAG);
        wakeLock.acquire();
    }

    private void releaseWakeLock() {
        wakeLock.release();
    }
}
