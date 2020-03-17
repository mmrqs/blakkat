package fr.efrei.android.blakkat.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.app.AlarmManager;
import android.app.PendingIntent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.ui.activities.LoginActivity;

/**
 * Send a notification to the user
 */
public class AlarmSendNotifs extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 666;
    public static final String CHANNEL_ID = "channel_notifs_blakkat";

    /**
     * Build the notification and notify the user through a channel created
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent goToBlakkat = new Intent(context, LoginActivity.class);
        goToBlakkat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, goToBlakkat, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_blakkat_dark)
                .setContentTitle("Blakkat")
                .setContentText("Hey! Have you seen anything lately ? :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * Set the timer for the alarm
     * @param context
     */
    public void setAlarm(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmSendNotifs.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 60, pi);
    }
}
