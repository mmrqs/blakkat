package fr.efrei.android.blakkat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import fr.efrei.android.blakkat.consuming.providers.ProviderHelper;
import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.model.Manga;
import fr.efrei.android.blakkat.model.Show;
import fr.efrei.android.blakkat.view.Adapters.CardAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SearchView r = (SearchView) findViewById(R.id.searchBar);

        r.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                String t = r.getQuery().toString();

                ProviderHelper providerHelper = new ProviderHelper();

                List<IMedia> results = new ArrayList<>();

                providerHelper.getGeneralProvider().getShowProvider()
                        .searchFor5Results(t,5).enqueue(new Callback<List<Show>>() {
                    @Override
                    public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                        results.addAll(response.body());
                        toastage(String.valueOf(results.size()));
                        mAdapter = new CardAdapter(results);
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Show>> call, Throwable t) {
                        Log.e("", t.toString());
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        }
        );

        /*r.setOnSearchClickListener(view -> {

            String t = r.getQuery().toString();

            ProviderHelper providerHelper = new ProviderHelper();

            List<IMedia> results = new ArrayList<>();

            providerHelper.getGeneralProvider().getMangaProvider()
                    .searchFor(t).enqueue(new Callback<List<Manga>>() {
                @Override
                public void onResponse(Call<List<Manga>> call, Response<List<Manga>> response) {
                    results.addAll(response.body());
                    toastage(String.valueOf(results.size()));
                }

                @Override
                public void onFailure(Call<List<Manga>> call, Throwable t) {
                    Log.e("", t.toString());
                }
            });
            mAdapter = new CardAdapter(results);
            recyclerView.setAdapter(mAdapter);

        });*/






        /*List<IMedia> t = new ArrayList<>();
        t.add(new Show("coucou"));
        t.add(new Show("arrow"));

        mAdapter = new CardAdapter(t);
        recyclerView.setAdapter(mAdapter);*/

    }

    public void toastage(String truc) {
        Toast.makeText(this, truc, Toast.LENGTH_LONG).show();
    }



}
