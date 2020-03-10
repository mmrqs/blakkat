package fr.efrei.android.blakkat.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.view.Adapters.CardAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Media> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_medias);

        findViewById(R.id.buttonViewed)
                .setOnClickListener(v -> startActivity(
                        new Intent(SearchActivity.this,
                            ViewedActivity.class)));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(createNewQueryTextListener(searchBar));
    }

    private SearchView.OnQueryTextListener createNewQueryTextListener(SearchView searchBar) {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                results = new ArrayList<>();
                String textSearched = searchBar.getQuery().toString();
                searchBar.clearFocus();

                KeeperFactory.getKeeper().getProviders()
                        .forEach(p -> p.searchForNbResults(textSearched,5)
                                .enqueue(createNewCallback()));

                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        };
    }

    private Callback<List<Media>> createNewCallback() {
        return new Callback<List<Media>>() {
            @Override
            public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                if(response.body() != null) {
                    results.addAll(response.body());
                    mAdapter = new CardAdapter(results, SearchActivity.this);
                    recyclerView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Media>> call, Throwable t) {
                t.printStackTrace();
                Log.e("Err", t.getLocalizedMessage());
            }
        };
    }
}
