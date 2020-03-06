package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.MediaRecord;

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
        Media media = Objects.requireNonNull(mediaChosen
                .getExtras())
                .getParcelable("MediaClicked");

        MediaRecord mediaRecord = new MediaRecord(media);
        Long id = mediaRecord.save();

        long i = MediaRecord.count(MediaRecord.class);
        Iterator<MediaRecord> it = MediaRecord.findAll(MediaRecord.class);
        mediaRecord = it.next();
        mediaRecord = MediaRecord.findById(MediaRecord.class, id);
        mediaRecord = MediaRecord.findById(MediaRecord.class, 5);
        mediaRecord = MediaRecord.find(MediaRecord.class, "identifier = ?",
                String.valueOf(media.getId())).get(0);

        titleDisplay = findViewById(R.id.titleDisplay);
        titleDisplay.setText(media.getTitle() + "    " + mediaRecord.getType());

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
