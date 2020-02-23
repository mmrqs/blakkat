package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import fr.efrei.android.blakkat.model.manager.UserManager;
import fr.efrei.android.blakkat.model.provider.IShowProvider;
import fr.efrei.android.blakkat.model.provider.ShowWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnSignup;
    private EditText editTextPseudo;
    private Button btnSignin;
    private UserManager userManager;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btnSignup = findViewById(R.id.signup);
        editTextPseudo = findViewById(R.id.editText_Pseudo);
        btnSignin = findViewById(R.id.signin);
        pref = getSharedPreferences("Pseudo", MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.betaseries.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IShowProvider provider = retrofit.create(IShowProvider.class);

        provider.getOne(1).enqueue(new Callback<ShowWrapper>() {
            @Override
            public void onResponse(Call<ShowWrapper> call, Response<ShowWrapper> response) {
                editTextPseudo.setText(response.body().getShow().getTitle());
            }

            @Override
            public void onFailure(Call<ShowWrapper> call, Throwable t) {
                t.printStackTrace();
            }
        });

        btnSignin.setOnClickListener(view -> signin(editTextPseudo.getText().toString()));

        btnSignup.setOnClickListener(view -> signup());
    }

    public void signup() {
        Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(signupIntent);
    }

    public void signin(String pseudo) {
        userManager = new UserManager(this);
        userManager.open();

        if(userManager.checkUser(pseudo)) {

            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Pseudo",pseudo);
            editor.commit();

            Intent homeIntent = new Intent(MainActivity.this, Home.class);
            startActivity(homeIntent);
        } else {
            editTextPseudo.setError("Wrong pseudo");
        }

        userManager.close();
    }
}
