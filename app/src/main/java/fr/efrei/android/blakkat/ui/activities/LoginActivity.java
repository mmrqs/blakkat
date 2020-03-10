package fr.efrei.android.blakkat.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.ui.fragments.SignInFragment;
import fr.efrei.android.blakkat.ui.fragments.SignUpFragment;
import fr.efrei.android.blakkat.model.User;

public class LoginActivity extends AppCompatActivity
        implements SignInFragment.SignInActionsListener, SignUpFragment.SignUpActionsListener {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("user_pseudo", MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        changeSignFragment(new SignInFragment());
    }

    @Override
    public boolean onSignIn(String pseudo) {
        if(User.exists(pseudo)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            this.startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean onSignUp(String pseudo) {
        if(!User.exists(pseudo)) {
            User current = new User(pseudo);
            current.save();

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            changeSignFragment(new SignInFragment());
            return true;
        }
        return false;
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
