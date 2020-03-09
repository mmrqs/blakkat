package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.MediaRecord;
import fr.efrei.android.blakkat.model.Show;
import fr.efrei.android.blakkat.view.Adapters.CardAdapter;
import fr.efrei.android.blakkat.view.Adapters.SeriesAdvancementAdapter;

public class DisplayActivity extends AppCompatActivity {
    private TextView titleDisplay;
    private ImageView imageView;
    private TextView time;
    private TextView synopsis;
    private Button returnButton;
    private TextView genre;
    private Button viewedToggleButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display);

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

        //TODO : delete this
        if(result.getProviderHint().equals("Show")) {
            System.out.println((HashMap) result.getSeasons());
        }
        if(result.getProviderHint().equals("Anime")) {
            System.out.println((Integer) result.getSeasons());
        }

        recyclerView = findViewById(R.id.RecyclerView_ActivityDisplay);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Seasons cards :
        switch (result.getProviderHint()) {
            case "Show" :
                mAdapter = new SeriesAdvancementAdapter(seasonsFormatter(result.getSeasons()),
                        DisplayActivity.this);
                break;
            case "Anime" :
            case "Manga" :
                mAdapter = new SeriesAdvancementAdapter(mangaAnimeFormatter(result.getSeasons()),
                        DisplayActivity.this);
                break;
            default :
                break;
        }

        recyclerView.setAdapter(mAdapter);


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

    public ArrayList<String> seasonsFormatter(HashMap<Integer, Integer> h) {
        ArrayList<String> a = new ArrayList<>();
        for (int i : h.keySet()) {
            for (int j = 1; j <= h.get(i) ; j++) {
                a.add("Saison : " + i + " Episode " + j);
            }
        }
        return a;
    }

    public ArrayList<String> mangaAnimeFormatter ( int nbVolumes ) {
        ArrayList<String> a = new ArrayList<>();
        for (int i = 1; i <= nbVolumes; i++) {
            a.add("Volume : " + i);
        }
        return a;
    }
}