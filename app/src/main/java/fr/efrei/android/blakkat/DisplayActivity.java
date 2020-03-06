package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import fr.efrei.android.blakkat.consuming.providers.IAnimeProvider;
import fr.efrei.android.blakkat.consuming.providers.IMangaProvider;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Anime;
import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.model.Manga;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayActivity extends AppCompatActivity {
    private TextView titleDisplay;
    private ImageView imageView;
    private TextView time;
    private TextView synopsis;
    private Button returnButton;
    private IMedia result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent mediaChosen = getIntent();
        IMedia result = Objects.requireNonNull(mediaChosen
                .getExtras())
                .getParcelable("MediaClicked");


        titleDisplay = findViewById(R.id.titleDisplay);
        titleDisplay.setText(result.getTitle());

        imageView = findViewById(R.id.imageCard_displayActivity);
        Picasso.with(imageView.getContext())
                .load(result.getImageUrl())
                .centerCrop().fit().into(imageView);

        time = findViewById(R.id.time);
        time.setText(result.getReleaseDate().toString());

        synopsis = findViewById(R.id.SynopsisContent_Display);
        synopsis.setText(result.getSynopsis());

        returnButton = findViewById(R.id.return_displayActivity);
        returnButton.setOnClickListener(view -> finish());
    }
}