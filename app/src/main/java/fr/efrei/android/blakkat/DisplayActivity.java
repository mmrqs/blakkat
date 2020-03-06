package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.SugarRecord;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
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
        SugarRecord.save(mediaRecord);


        long i = SugarRecord.count(MediaRecord.class);
        Iterator<MediaRecord> it = SugarRecord.findAll(MediaRecord.class);
        mediaRecord = it.next();
        mediaRecord = SugarRecord.findById(MediaRecord.class, 5L);
        mediaRecord = SugarRecord.find(MediaRecord.class, "identifier = ?",
                String.valueOf(media.getId()))
                .get(0);

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
