package fr.efrei.android.blakkat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import java.util.ArrayList;

import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.MediaRecord;
import fr.efrei.android.blakkat.view.Adapters.CardAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewedActivity extends AppCompatActivity {
    private ArrayList<Media> seen = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView = findViewById(R.id.viewed_medias);
        this.recyclerView.setLayoutManager(layoutManager);

        MediaRecord.findAll(MediaRecord.class)
                .forEachRemaining(mr -> mr.getCorresponding()
                        .enqueue(createNewCallback()));
    }

    private Callback<Media> createNewCallback() {
        return new Callback<Media>() {
            @Override
            public void onResponse(Call<Media> call, Response<Media> response) {
                if(response.body() != null) {
                    seen.add(response.body());
                    adapter = new CardAdapter(seen, ViewedActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Media> call, Throwable t) {
                t.printStackTrace();
                Log.e("Err", t.getLocalizedMessage());
            }
        };
    }

}
