package pl.mate00.sleeperfriendlyapp.ui;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;

/**
 * Created by mamy on 16.02.15.
 */
public interface AddAlarmUiCase {

    void updateUiWithAlarm(RepeatableAlarm alarm);

    void onErrorAfterAdding(String message);
}
