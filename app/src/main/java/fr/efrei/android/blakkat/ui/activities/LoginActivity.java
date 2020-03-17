package fr.efrei.android.blakkat.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.helpers.SessionHelper;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import fr.efrei.android.blakkat.ui.fragments.SignInFragment;
import fr.efrei.android.blakkat.ui.fragments.SignUpFragment;

/**
 * This activity will be the first seen by the user ; it controls {@link SignUpFragment} and
 * {@link SignUpFragment}
 */
public class LoginActivity extends AppCompatActivity
        implements SignInFragment.SignInActionsListener, SignUpFragment.SignUpActionsListener {

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionHelper.setupPreferences(getPreferences(MODE_PRIVATE));
        setContentView(R.layout.activity_login);
        changeSignFragment(new SignInFragment());
    }

    /**
     * Tries to sign the given pseudo in ; upon success, signals it to the caller
     * @param pseudo that will be signed in
     * @return true if signed in, false if not
     */
    @Override
    public boolean onSignIn(String pseudo) {
        UserRecord u = UserRecord.exists(pseudo);
        if(u != null) {
            SessionHelper.save(getResources().getString(R.string.user), u);
            KeeperFactory.configureFactory(u.isEighteen());

            this.startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return false;
    }

    /**
     * Tries to sign the given pseudo up ; upon success, signals it to the caller
     * @param pseudo that will be signed up
     * @return the reason of failure or nothing
     */
    @Override
    public String onSignUp(String pseudo) {
        if(pseudo.equals("")) {
            return getResources().getString(R.string.pseudo_invalid);
        } else if (UserRecord.exists(pseudo) == null) {
            UserRecord current = new UserRecord(pseudo);
            current.save();
            changeSignFragment(new SignInFragment());
            return null;
        }
        return getResources().getString(R.string.pseudo_exists);
    }

    /**
     * Allows this activity to swap to {@link SignUpFragment} when requested
     */
    @Override
    public void onSignUpRequest() {
        changeSignFragment(new SignUpFragment());
    }

    /**
     * Allows this activity to swap to {@link SignInFragment} when requested
     */
    @Override
    public void onSignInRequest() {
        changeSignFragment(new SignInFragment());
    }

    /**
     * Swaps the current {@link Fragment} for the provided one
     * @param fragment that will be displayed
     */
    private void changeSignFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_frameLayout_sign, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}
