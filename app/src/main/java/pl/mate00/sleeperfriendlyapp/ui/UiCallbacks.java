package pl.mate00.sleeperfriendlyapp.ui;

import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;

/**
 * Created by mamy on 26.02.15.
 */
public interface UiCallbacks {

    void updateUiWithAlarm(RepeatableAlarm alarm);

    void updateUiAfterDelete(RepeatableAlarm alarm);

    void onErrorAfterAdding(String message);

    void onErrorAfterDelete(String message);

    void onUpdateAfterSwitching(RepeatableAlarm alarm);
}
