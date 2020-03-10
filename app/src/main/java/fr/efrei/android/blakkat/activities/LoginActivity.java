package fr.efrei.android.blakkat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.Toaster;
import fr.efrei.android.blakkat.model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextPseudo;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        editTextPseudo = findViewById(R.id.editText_Pseudo);
        pref = getSharedPreferences("user_pseudo", MODE_PRIVATE);

        Button btnSignUp = findViewById(R.id.signup);
        Button btnSignIn = findViewById(R.id.signin);
        btnSignIn.setOnClickListener(view -> signIn(editTextPseudo.getText().toString()));
        editTextPseudo.setOnEditorActionListener((textView, i, keyEvent) -> {
            signIn(editTextPseudo.getText().toString());
            return true;
        });
        btnSignUp.setOnClickListener(view -> signUp());
        editTextPseudo.requestFocus();
    }

    public void signUp() {
        startActivity(new Intent(LoginActivity.this,
                SignUpActivity.class));
    }

    public void signIn(String pseudo) {
        if(User.exists(pseudo)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            Toaster.toast(this, String.format(getResources()
                            .getString(R.string.pseudo_welcome),
                    pseudo));

            startActivity(new Intent(LoginActivity.this,
                    SearchActivity.class));
        } else {
            editTextPseudo.setError(getResources()
                    .getString(R.string.pseudo_wrong));
        }
    }
}
