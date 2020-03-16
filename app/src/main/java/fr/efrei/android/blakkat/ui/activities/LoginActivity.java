package fr.efrei.android.blakkat.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.SessionHelper;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import fr.efrei.android.blakkat.ui.fragments.SignInFragment;
import fr.efrei.android.blakkat.ui.fragments.SignUpFragment;

public class LoginActivity extends AppCompatActivity
        implements SignInFragment.SignInActionsListener, SignUpFragment.SignUpActionsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionHelper.setupPreferences(getPreferences(MODE_PRIVATE));
        setContentView(R.layout.activity_login);
        changeSignFragment(new SignInFragment());

    }

    @Override
    public boolean onSignIn(String pseudo) {
        UserRecord u = UserRecord.exists(pseudo);
        if(u != null) {
            SessionHelper.save(getResources().getString(R.string.user), u);

            this.startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return false;
    }

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


    @Override
    public void onSignUpRequest() {
        changeSignFragment(new SignUpFragment());
    }

    @Override
    public void onSignInRequest() {
        changeSignFragment(new SignInFragment());
    }

    private void changeSignFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_frameLayout_sign, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}
