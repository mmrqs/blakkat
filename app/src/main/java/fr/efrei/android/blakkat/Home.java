package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private SharedPreferences prf;
    private TextView pseudoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prf = getSharedPreferences("Pseudo",MODE_PRIVATE);
        pseudoTextView = findViewById(R.id.textViewHome);

        pseudoTextView.setText("Coucou ô secrétaire "+ prf.getString("Pseudo", null));

    }
}
