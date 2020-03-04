package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.efrei.android.blakkat.model.IMedia;

public class displayActivity extends AppCompatActivity {
    private TextView title;
    private ImageView imageView;
    private TextView time;
    private TextView synopsis;
    private Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent mediaChosen = getIntent();
        IMedia media = (IMedia) mediaChosen.getExtras().getParcelable("MediaClicked");

        title = findViewById(R.id.titleDisplay);
        title.setText(media.getTitle());

        imageView = findViewById(R.id.imageCard_displayActivity);
        Picasso.with(imageView.getContext()).load(media.getImageUrl()).centerCrop().fit().into(imageView);

        if(media.getReleaseDate() != null) {
            time = findViewById(R.id.time);
            time.setText(media.getGenres() + " / " + media.getReleaseDate().toString());
        }

        synopsis = findViewById(R.id.SynopsisContent_Display);
        synopsis.setText(media.getSynopsis());

        returnButton = findViewById(R.id.return_displayActivity);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
