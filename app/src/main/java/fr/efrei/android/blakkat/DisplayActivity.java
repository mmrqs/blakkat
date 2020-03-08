package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.SugarRecord;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.MediaRecord;

public class DisplayActivity extends AppCompatActivity {
    private TextView titleDisplay;
    private ImageView imageView;
    private TextView time;
    private TextView synopsis;
    private Button returnButton;
    private TextView genre;
    private Media result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent mediaChosen = getIntent();
        Media media = Objects.requireNonNull(mediaChosen
                .getExtras())
                .getParcelable("MediaClicked");

        MediaRecord mediaRecord = new MediaRecord(media);
        SugarRecord.save(mediaRecord);

        mediaRecord = SugarRecord.find(MediaRecord.class, "identifier = ?",
                String.valueOf(media.getId()))
                .get(0);

        titleDisplay = findViewById(R.id.titleDisplay);
        titleDisplay.setText(media.getTitle() + "    " + mediaRecord.getType());
        Media result = Objects.requireNonNull(mediaChosen
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

        System.out.println("genre"+ media.getGenres());
        genre = findViewById(R.id.genre);
        genre.setText(media.getGenres().toString());

        synopsis = findViewById(R.id.SynopsisContent_Display);
        synopsis.setText(result.getSynopsis());

        returnButton = findViewById(R.id.return_displayActivity);
        returnButton.setOnClickListener(view -> finish());
    }
}