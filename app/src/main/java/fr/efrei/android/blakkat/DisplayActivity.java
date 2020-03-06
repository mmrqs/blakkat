package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import fr.efrei.android.blakkat.model.IMedia;

public class DisplayActivity extends AppCompatActivity {
    private TextView titleDisplay;
    private ImageView imageView;
    private TextView time;
    private TextView synopsis;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent mediaChosen = getIntent();
        IMedia media = Objects.requireNonNull(mediaChosen
                .getExtras())
                .getParcelable("MediaClicked");

        titleDisplay = findViewById(R.id.titleDisplay);
        titleDisplay.setText(media.getTitle());

        imageView = findViewById(R.id.imageCard_displayActivity);
        Picasso.with(imageView.getContext())
                .load(media.getImageUrl())
                .centerCrop().fit().into(imageView);

        time = findViewById(R.id.time);
        time.setText(media.getReleaseDate().toString());

        synopsis = findViewById(R.id.SynopsisContent_Display);
        synopsis.setText(media.getSynopsis());

        returnButton = findViewById(R.id.return_displayActivity);
        returnButton.setOnClickListener(view -> finish());
    }
}
