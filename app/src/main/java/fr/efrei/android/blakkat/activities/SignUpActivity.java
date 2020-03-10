package fr.efrei.android.blakkat.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.Toaster;
import fr.efrei.android.blakkat.model.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextPseudo;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextPseudo = findViewById(R.id.editTextPseudo);
        pref = getSharedPreferences("user_pseudo", MODE_PRIVATE);

        findViewById(R.id.btnReturn).setOnClickListener(view -> back());
        findViewById(R.id.btnSignup_activitySignup)
                .setOnClickListener(view -> signUp(editTextPseudo
                        .getText().toString()));

        editTextPseudo.setOnEditorActionListener((textView, i, keyEvent) -> {
            signUp(editTextPseudo.getText().toString());
            return true;
        });
    }

    public void back() {
        setContentView(R.layout.activity_main);

        Intent mainIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(mainIntent);
    }

    public void signUp(String pseudo) {
        if(!User.exists(pseudo)) {
            User current = new User(pseudo);
            current.save();

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            Intent mainIntent = new Intent(SignUpActivity.this, LoginActivity.class);
            Toaster.toast(this, getResources().getString(R.string.pseudo_created, pseudo));
            startActivity(mainIntent);
        } else {
            editTextPseudo.setError(getResources().getString(R.string.pseudo_exists));
        }
    }
}
