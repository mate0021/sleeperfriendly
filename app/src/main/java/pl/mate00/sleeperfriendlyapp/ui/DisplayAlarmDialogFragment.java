package pl.mate00.sleeperfriendlyapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by mamy on 08.04.15.
 */
public class DisplayAlarmDialogFragment extends DialogFragment {

    private static final String TAG = DisplayAlarmDialogFragment.class.getSimpleName();

    private AlarmReactionListener listener;

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
    }
}
