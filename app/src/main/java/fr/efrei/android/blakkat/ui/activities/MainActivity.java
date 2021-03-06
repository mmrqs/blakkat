package fr.efrei.android.blakkat.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.Toaster;
import fr.efrei.android.blakkat.ui.fragments.DisplayMediaFragment;
import fr.efrei.android.blakkat.ui.fragments.HomeFragment;
import fr.efrei.android.blakkat.ui.fragments.SearchMediasFragment;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.ui.fragments.TimelineFragment;
import fr.efrei.android.blakkat.ui.fragments.ViewedMediasFragment;
import fr.efrei.android.blakkat.ui.views.MediaAdapter;

/**
 * This activity is the principal one, as it will be shown in virtualy every standard use case,
 * beside settings and login
 */
public class MainActivity extends AppCompatActivity
        implements MediaAdapter.DisplayActionsListener,
        DisplayMediaFragment.MediaLoadedListener {

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // bottom navigation options
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.action_search:
                            changeFragment(new SearchMediasFragment());
                            return true;
                        case R.id.action_home:
                            changeFragment(new HomeFragment());
                            return true;
                        case R.id.action_media_seen:
                            changeFragment(new ViewedMediasFragment());
                            return true;
                        case android.R.id.home:
                            onBackPressed();
                            return true;
                        case R.id.action_timeline:
                            changeFragment(new TimelineFragment());
                            return true;
                        default:
                            Toaster.burn(this, "Error");
                            return false;
                    }
                });

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        changeFragment(new HomeFragment(), false);
    }

    /**
     * Overflown menu
     * {@inheritDoc}
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_overflow_menu, menu);
        return true;
    }

    /**
     * Toolbar actions
     * {@inheritDoc}
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                Toaster.burn(this, "Error");
                return false;
        }
    }

    /**
     * Displays a new {@link DisplayMediaFragment} whith the informations of the chosen {@link Media}
     * @param media chosen {@link Media}
     */
    @Override
    public void onMediaChosen(Media media) {
        changeFragment(new DisplayMediaFragment(media));
    }

    /**
     * Displays a toast when the {@link Media} currently displayed in a {@link DisplayMediaFragment} finishes loading
     */
    @Override
    public void onMediaLoaded(Media media) {
        Toaster.toast(this, media.getTitle() + " has finished loading");
    }

    /**
     * Ease the change between two fragments
     * Will add it to the backStack
     * @param fragment target fragment (will be shown)
     */
    private void changeFragment(Fragment fragment) {
        changeFragment(fragment, true);
    }


    /**
     * Ease the change between two fragments
     * @param fragment target fragment (will be shown)
     */
    private void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = this.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.main_frameLayout_fragment, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if(addToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }
}