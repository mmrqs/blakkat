package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import fr.efrei.android.blakkat.model.Record.User;

public class SignupActivity extends AppCompatActivity {
    private Button btnReturn;
    private Button btnSignup;
    private EditText editTextPseudo;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnReturn = findViewById(R.id.btnReturn);
        btnSignup = findViewById(R.id.btnSignup_activitySignup);
        editTextPseudo = findViewById(R.id.editTextSignup_activitySignup);
        pref = getSharedPreferences("user_pseudo", MODE_PRIVATE);

        btnReturn.setOnClickListener(view -> back());

        btnSignup.setOnClickListener(view -> signup(editTextPseudo.getText().toString()));

        editTextPseudo.setOnEditorActionListener((textView, i, keyEvent) -> {
            signup(editTextPseudo.getText().toString());
            return true;
        });
    }

    public void back() {
        setContentView(R.layout.activity_main);

        Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void signup(String pseudo) {
        if(!User.exists(pseudo)) {
            User current = new User(pseudo);
            current.save();

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            Intent homeIntent = new Intent(SignupActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        } else {
            editTextPseudo.setError("This pseudo already exists");
        }
    }

}
