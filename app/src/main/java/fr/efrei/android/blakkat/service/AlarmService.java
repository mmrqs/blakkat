package fr.efrei.android.blakkat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Service for our alarm sending notifications
 */
public class AlarmService extends Service {
    private AlarmSendNotifs alarmSendNotifs = new AlarmSendNotifs();
    
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarmSendNotifs.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        alarmSendNotifs.setAlarm(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
