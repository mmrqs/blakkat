package fr.efrei.android.blakkat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartAlarmNotifications extends BroadcastReceiver {
    private AlarmSendNotifs alarmSendNotifs = new AlarmSendNotifs();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            alarmSendNotifs.setAlarm(context);
        }
    }
}