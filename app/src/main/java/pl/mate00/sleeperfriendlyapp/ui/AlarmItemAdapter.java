package pl.mate00.sleeperfriendlyapp.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextClock;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

import pl.mate00.sleeperfriendlyapp.R;
import pl.mate00.sleeperfriendlyapp.RepeatableAlarm;

/**
 * Created by mamy on 10.02.15.
 */
public class AlarmItemAdapter extends ArrayAdapter<RepeatableAlarm> {

    private Context context;
    private int rowLayoutResourceId;
    private List<RepeatableAlarm> alarms;

    public AlarmItemAdapter(Context context, int resource, List<RepeatableAlarm> alarmsList) {
        super(context, resource, alarmsList);
        this.context = context;
        this.rowLayoutResourceId = resource;
        this.alarms = alarmsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RepeatableAlarmHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(rowLayoutResourceId, parent, false);

            holder = new RepeatableAlarmHolder();
            holder.chBoxIsEnabled = (CheckBox) row.findViewById(R.id.is_enabled);
            holder.txtClock = (TextView) row.findViewById(R.id.alarm_time);
            holder.txtDays = (TextView) row.findViewById(R.id.alarm_days);

            row.setTag(holder);
        } else {
            holder = (RepeatableAlarmHolder) row.getTag();
        }

        RepeatableAlarm alarm = alarms.get(position);
        holder.chBoxIsEnabled.setChecked(alarm.isEnabled());
        holder.txtClock.setText(alarm.formatAlarmTime("HH:mm"));
        holder.txtDays.setText(alarm.formatDays());

        return row;
    }

    static class RepeatableAlarmHolder {
        CheckBox chBoxIsEnabled;
        TextView txtClock;
        TextView txtDays;
    }
}