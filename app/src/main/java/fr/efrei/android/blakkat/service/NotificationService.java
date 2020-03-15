package fr.efrei.android.blakkat.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import fr.efrei.android.blakkat.R;

public class NotificationService extends Service {

    private static final int NOTIFICATION_ID = 666;
    public static final String CHANNEL_ID = "channel_notifs_blakkat";

    public void onCreate() {

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("coucou");
        return START_STICKY;
    }

    public void onDestroy() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_blakkat_dark)
                .setContentTitle("Blakkat")
                .setContentText("Wesh revient stp")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

