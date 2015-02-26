package pl.mate00.sleeperfriendlyapp.ui;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;

/**
 * Created by mamy on 26.02.15.
 */
public interface DeleteAlarmUiCase {

    void updateUiOnDeleteAlarm(RepeatableAlarm alarm);

    void onErrorAfterDeleting(String message);
}
