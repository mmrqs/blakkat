package fr.efrei.android.blakkat.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_blakkat_dark)
                .setContentTitle("Blakkat")
                .setContentText("Bienvenue sur Blakkat ! ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

