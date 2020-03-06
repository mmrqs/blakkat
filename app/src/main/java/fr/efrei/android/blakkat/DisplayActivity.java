package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Button viewedToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display);

        Intent mediaChosen = getIntent();
        Media media = Objects.requireNonNull(mediaChosen
                .getExtras())
                .getParcelable("MediaClicked");

        titleDisplay = findViewById(R.id.titleDisplay);
        titleDisplay.setText(media.getTitle() + " — " + media.getProviderHint());

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

        viewedToggleButton = findViewById(R.id.viewed_toggle);

        this.changeToggleViewedButtonContents(MediaRecord
                .exists(media.getId(), media.getProviderHint()));

        viewedToggleButton.setOnClickListener(view -> {
            MediaRecord record = MediaRecord.exists(media.getId(), media.getProviderHint());
            if(record == null) {
                record = new MediaRecord(media);
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
