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
    private Button btnSignup;
    private EditText editTextPseudo;
    private Button btnSignin;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btnSignup = findViewById(R.id.signup);
        editTextPseudo = findViewById(R.id.editText_Pseudo);
        btnSignin = findViewById(R.id.signin);
        pref = getSharedPreferences("user_pseudo", MODE_PRIVATE);

        btnSignin.setOnClickListener(view -> signIn(editTextPseudo.getText().toString()));
        editTextPseudo.setOnEditorActionListener((textView, i, keyEvent) -> {
            signIn(editTextPseudo.getText().toString());
            return true;
        });
        btnSignup.setOnClickListener(view -> signUp());
        editTextPseudo.requestFocus();
    }

    public void signUp() {
        startActivity(new Intent(MainActivity.this,
                SignUpActivity.class));
    }

    public void signIn(String pseudo) {
        if(User.exists(pseudo)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            toasting(String.format(getResources()
                            .getString(R.string.pseudo_welcome),
                    pseudo));

            startActivity(new Intent(MainActivity.this,
                    SearchActivity.class));
        } else {
            editTextPseudo.setError(getResources()
                    .getString(R.string.pseudo_wrong));
        }
    }

    public void toasting(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
