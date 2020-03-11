package fr.efrei.android.blakkat.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.Toaster;
import fr.efrei.android.blakkat.ui.fragments.DisplayMediaFragment;
import fr.efrei.android.blakkat.ui.fragments.SearchMediasFragment;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.ui.fragments.ViewedMediasFragment;
import fr.efrei.android.blakkat.ui.views.CardAdapter;

public class MainActivity extends AppCompatActivity
        implements SearchMediasFragment.SearchActionsListener, CardAdapter.DisplayActionsListener,
        DisplayMediaFragment.MediaLoadedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new SearchMediasFragment());
    }

    @Override
    public void onViewedRequest() {
        changeFragment(new ViewedMediasFragment());
    }

    @Override
    public void onMediaChosen(Media media) {
        changeFragment(new DisplayMediaFragment(media, this));
    }

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