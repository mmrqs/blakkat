package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.efrei.android.blakkat.consuming.providers.ProviderHelper;
import fr.efrei.android.blakkat.model.Manga;
import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ProviderHelper providerHelper = new ProviderHelper();

        List<IMedia> results = new ArrayList<>();

        providerHelper.getGeneralProvider().getMangaProvider()
                .searchFor("Tengen").enqueue(new Callback<List<Manga>>() {
            @Override
            public void onResponse(Call<List<Manga>> call, Response<List<Manga>> response) {
                results.addAll(response.body());
                toastage(String.valueOf(results.size()));
            }

            @Override
            public void onFailure(Call<List<Manga>> call, Throwable t) {
                Log.e("", t.toString());
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
        if(User.exists(pseudo)) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user_pseudo", pseudo);
            editor.apply();

            Intent homeIntent = new Intent(MainActivity.this, Home.class);
            startActivity(homeIntent);
        } else {
            editTextPseudo.setError("Wrong pseudo");
        }
    }

    public void toastage(String truc) {
        Toast.makeText(this, truc, Toast.LENGTH_LONG).show();
    }
}
