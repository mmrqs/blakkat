package fr.efrei.android.blakkat.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.IProvider;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Anime;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.ui.views.MediaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMediasFragment extends Fragment {
    private MediaAdapter.DisplayActionsListener displayActionsListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Media> results;

    private CheckBox animesCheckBox;
    private CheckBox mangasCheckbox;
    private CheckBox moviesCheckbox;
    private CheckBox showsCheckbox;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.displayActionsListener = (MediaAdapter.DisplayActionsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SearchActionsListener and DisplayActionsListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_medias, container, false);

        initRegistry(view);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        SearchView searchBar = view.findViewById(R.id.searchMedias_searchBar);
        searchBar.setOnQueryTextListener(createNewQueryTextListener(searchBar));

        return view;
    }

    private void initRegistry(View view) {
        animesCheckBox = view.findViewById(R.id.searchMedias_checkBox_animes);
        mangasCheckbox = view.findViewById(R.id.searchMedias_checkBox_mangas);
        moviesCheckbox = view.findViewById(R.id.searchMedias_checkBox_movies);
        showsCheckbox = view.findViewById(R.id.searchMedias_checkBox_shows);
    }

    private SearchView.OnQueryTextListener createNewQueryTextListener(SearchView searchBar) {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBar.clearFocus();
                results = new ArrayList<>();
                String textSearched = searchBar.getQuery().toString();

                getNonExcludedProviders()
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
            public void onResponse(@NonNull Call<List<Media>> call, @NonNull Response<List<Media>> response) {
                if(response.body() != null) {
                    results.addAll(response.body());
                    adapter = new MediaAdapter(results, displayActionsListener);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Media>> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("Err", t.getLocalizedMessage());
            }
        };
    }

    private List<IProvider> getNonExcludedProviders() {
        ArrayList<IProvider> includedProviders = new ArrayList<>();
        if(animesCheckBox.isChecked()) {
            includedProviders.add(KeeperFactory.getKeeper().getAnimeProvider());
        }
        if(mangasCheckbox.isChecked()) {
            includedProviders.add(KeeperFactory.getKeeper().getMangaProvider());
        }
        if(moviesCheckbox.isChecked()) {
            includedProviders.add(KeeperFactory.getKeeper().getMovieProvider());
        }
        if(showsCheckbox.isChecked()) {
            includedProviders.add(KeeperFactory.getKeeper().getShowProvider());
        }
        return includedProviders;
    }
}
