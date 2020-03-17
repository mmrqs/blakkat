package fr.efrei.android.blakkat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * When the user will reboot his phone, this class will receive a signal and create an alarm.
 */
public class AutoStartAlarmNotifications extends BroadcastReceiver {
    private AlarmSendNotifs alarmSendNotifs = new AlarmSendNotifs();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            alarmSendNotifs.setAlarm(context);
        }
    }
}