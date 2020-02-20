package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.efrei.android.blakkat.model.User;
import fr.efrei.android.blakkat.model.manager.UserManager;

public class Signup extends AppCompatActivity {

    private Button btnReturn;
    private Button btnSignup;
    private EditText editTextPseudo;
    private UserManager userManager;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnReturn = findViewById(R.id.btnReturn);
        btnSignup = findViewById(R.id.btnSignup_activitySignup);
        editTextPseudo = findViewById(R.id.editTextSignup_activitySignup);
        pref = getSharedPreferences("Pseudo",MODE_PRIVATE);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Return();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup(editTextPseudo.getText().toString());
            }
        });
    }

    public void Return() {
        setContentView(R.layout.activity_main);

        Intent mainIntent = new Intent(Signup.this, MainActivity.class);
        startActivity(mainIntent);
    }

    public void Signup(String pseudo) {
        userManager = new UserManager(this);
        userManager.open();

        if(!userManager.checkUser(pseudo)) {
            userManager.addUser(new User(pseudo));

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Pseudo",pseudo);
            editor.commit();

            Intent homeIntent = new Intent(Signup.this, Home.class);
            startActivity(homeIntent);
        } else {
            editTextPseudo.setError("This pseudo already exists");
        }
        userManager.close();
    }

}
