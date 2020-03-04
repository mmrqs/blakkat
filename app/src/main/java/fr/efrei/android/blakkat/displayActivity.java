package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import fr.efrei.android.blakkat.model.IMedia;

public class displayActivity extends AppCompatActivity {
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent mediaChosen = getIntent();
        IMedia media = (IMedia) mediaChosen.getExtras().getParcelable("MediaClicked");

        System.out.println(media.getTitle());
        title = findViewById(R.id.titleDisplay);
        title.setText(media.getTitle());

    }
}
