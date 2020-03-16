package fr.efrei.android.blakkat.ui.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.helpers.SessionHelper;
import fr.efrei.android.blakkat.helpers.Toaster;
import fr.efrei.android.blakkat.model.Record.UserRecord;

public class SettingsActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "channel_notifs_blakkat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        UserRecord currentUser = SessionHelper.get(getResources()
                .getString(R.string.user), UserRecord.class);

        ((Switch)findViewById(R.id.settings_switch_jikan))
                .setChecked(currentUser.isEighteen());

        ((Switch)findViewById(R.id.settings_switch_jikan))
                .setOnCheckedChangeListener((buttonView, isChecked) -> {
                    KeeperFactory.updateJikanSetting(isChecked);

                    currentUser.setEighteen(isChecked);
                    currentUser.save();
                    SessionHelper.save(getResources()
                            .getString(R.string.user), currentUser);

                    Toaster.burn(this, isChecked ?
                            getResources().getString(R.string.eighteen_mode_activated) :
                            getResources().getString(R.string.eighteen_mode_desactivated));
                });

        ((Switch)findViewById(R.id.notifications))
                .setChecked(currentUser.isEnableNotifs());

        ((Switch)findViewById(R.id.notifications))
                .setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked)
                        createNotificationChannel();
                    else
                        deleteNotificationChannel();

                    currentUser.setEnableNotifs(isChecked);
                    currentUser.save();
                    SessionHelper.save(getResources()
                            .getString(R.string.user), currentUser);

                    Toaster.burn(this, isChecked ?
                            getResources().getString(R.string.notification_mode_activated) :
                            getResources().getString(R.string.notification_mode_desactivated));
                });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        Toaster.burn(this, "Error");
        return false;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void deleteNotificationChannel() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.deleteNotificationChannel(CHANNEL_ID);
    }
}