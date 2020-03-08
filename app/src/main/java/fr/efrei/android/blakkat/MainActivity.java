package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.efrei.android.blakkat.model.User;

public class MainActivity extends AppCompatActivity {
    private EditText editTextPseudo;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Button btnSignUp = findViewById(R.id.signup);
        Button btnSignIn = findViewById(R.id.signin);
        editTextPseudo = findViewById(R.id.editText_Pseudo);
        pref = getSharedPreferences("user_pseudo", MODE_PRIVATE);

        btnSignIn.setOnClickListener(view -> signIn(editTextPseudo.getText().toString()));
        editTextPseudo.setOnEditorActionListener((textView, i, keyEvent) -> {
            signIn(editTextPseudo.getText().toString());
            return true;
        });
        btnSignUp.setOnClickListener(view -> signUp());
    }

    public void signUp() {
        startActivity(new Intent(MainActivity.this,
                SignupActivity.class));
    }

    public void signIn(String pseudo) {
        if(User.exists(pseudo)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            startActivity(new Intent(MainActivity.this,
                    SearchActivity.class));
        } else {
            editTextPseudo.setError("Wrong pseudo");
        }
    }
}
