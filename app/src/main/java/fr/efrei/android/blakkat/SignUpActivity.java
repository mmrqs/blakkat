package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.efrei.android.blakkat.model.User;

public class SignUpActivity extends AppCompatActivity {
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

        btnSignup.setOnClickListener(view -> signUp(editTextPseudo.getText().toString()));

        editTextPseudo.setOnEditorActionListener((textView, i, keyEvent) -> {
            signUp(editTextPseudo.getText().toString());
            return true;
        });
    }

    public void back() {
        setContentView(R.layout.activity_main);

        Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void signUp(String pseudo) {
        if(!User.exists(pseudo)) {
            User current = new User(pseudo);
            current.save();

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
            toasting(getResources().getString(R.string.pseudo_created, pseudo));
            startActivity(mainIntent);
        } else {
            editTextPseudo.setError(getResources().getString(R.string.pseudo_exists));
        }
    }

    /**
     * Allows easy toast apparition
     * @param text to be displayed
     */
    public void toasting(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
