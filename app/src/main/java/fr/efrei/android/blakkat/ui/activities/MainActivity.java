package fr.efrei.android.blakkat.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.ui.fragments.DisplayMediaFragment;
import fr.efrei.android.blakkat.ui.fragments.SearchFragment;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.ui.fragments.ViewedFragment;
import fr.efrei.android.blakkat.ui.views.CardAdapter;

public class MainActivity extends AppCompatActivity implements SearchFragment.SearchActionsListener, CardAdapter.DisplayActionsListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new SearchFragment());
    }

    @Override
    public void onViewedRequest() {
        changeFragment(new ViewedFragment());
    }

    @Override
    public void onMediaChosen(Media media) {
        changeFragment(new DisplayMediaFragment(media));
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.main_frameLayout_fragment, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
