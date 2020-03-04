package fr.efrei.android.blakkat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.android.blakkat.consuming.providers.IProvider;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.view.Adapters.CardAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<IMedia> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SearchView search_bar = findViewById(R.id.searchBar);

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                String textSearched = search_bar.getQuery().toString();
                for (IProvider p : KeeperFactory.getKeeper().getProviders()) {
                    p.searchForNbResults(textSearched,5).enqueue(new Callback<List<IMedia>>() {
                        @Override
                        public void onResponse(Call<List<IMedia>> call, Response<List<IMedia>> response) {
                            results = new ArrayList<>();

                            if(response.body() != null){
                                results.addAll(response.body());
                                mAdapter = new CardAdapter(results, SearchActivity.this);
                                recyclerView.setAdapter(mAdapter);
                            }
                        }
                        @Override
                        public void onFailure(Call<List<IMedia>> call, Throwable t) {
                            t.printStackTrace();
                            Log.e("Err", t.getLocalizedMessage());
                        }
                    });
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

}