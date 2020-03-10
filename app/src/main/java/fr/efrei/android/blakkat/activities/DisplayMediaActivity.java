package fr.efrei.android.blakkat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.MediaRecord;

public class DisplayMediaActivity extends AppCompatActivity {
    private TextView titleDisplay;
    private ImageView imageView;
    private TextView time;
    private TextView synopsis;
    private Button returnButton;
    private TextView genre;
    private Button viewedToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_media);

        Intent mediaChosen = getIntent();
        Media result = Objects.requireNonNull(mediaChosen
                .getExtras())
                .getParcelable("MediaClicked");

        titleDisplay = findViewById(R.id.titleDisplay);
        assert result != null;
        titleDisplay.setText(result.getTitle());

        imageView = findViewById(R.id.imageCard_displayActivity);
        Picasso.with(imageView.getContext())
                .load(result.getImageUrl())
                .centerCrop().fit().into(imageView);

        time = findViewById(R.id.time);
        time.setText(result.getReleaseDate().toString());

        genre = findViewById(R.id.genre);
        genre.setText(result.getGenres().toString());

        synopsis = findViewById(R.id.SynopsisContent_Display);
        synopsis.setText(result.getSynopsis());

        returnButton = findViewById(R.id.return_displayActivity);
        returnButton.setOnClickListener(view -> finish());

        viewedToggleButton = findViewById(R.id.viewed_toggle);

        this.changeToggleViewedButtonContents(MediaRecord
                .exists(result.getId(), result.getProviderHint()));

        viewedToggleButton.setOnClickListener(view -> {
            MediaRecord record = MediaRecord.exists(result.getId(), result.getProviderHint());
            if(record == null) {
                record = new MediaRecord(result);
                record.save();
            } else {
                record.delete();
                record = null;
            }
            changeToggleViewedButtonContents(record);
        });
    }

    void changeToggleViewedButtonContents(MediaRecord mr) {
        viewedToggleButton.setText(mr == null ? R.string.notviewed : R.string.viewed);
    }
}