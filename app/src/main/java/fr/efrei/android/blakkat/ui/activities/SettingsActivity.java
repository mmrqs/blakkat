package fr.efrei.android.blakkat.ui.activities;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.helpers.SessionHelper;
import fr.efrei.android.blakkat.helpers.Toaster;
import fr.efrei.android.blakkat.model.Record.UserRecord;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.toolbar));

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
    }
}