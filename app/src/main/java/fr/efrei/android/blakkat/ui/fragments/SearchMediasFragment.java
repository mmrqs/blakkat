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
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.IProvider;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.ui.views.MediaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This {@link Fragment} allows the user to search {@link Media}
 */
public class SearchMediasFragment extends Fragment {
    private MediaAdapter.DisplayActionsListener displayActionsListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Media> results;

    private CheckBox animesCheckBox;
    private CheckBox mangasCheckbox;
    private CheckBox moviesCheckbox;
    private CheckBox showsCheckbox;
    private Switch switchOrderByTitle;

    /**
     * {@inheritDoc}
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.displayActionsListener = (MediaAdapter.DisplayActionsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SearchActionsListener and DisplayActionsListener");
        }
    }

    /**
     * {@inheritDoc}
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_medias, container, false);

        initRegistry(view);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        ((Switch)view.findViewById(R.id.searchMedias_switch))
                .setOnCheckedChangeListener((buttonView, isChecked) ->
                        buttonView.setText(isChecked ?
                                getResources().getString(R.string.searchMedias_switch_title) :
                                getResources().getString(R.string.searchMedias_switch_score)));

        SearchView searchBar = view.findViewById(R.id.searchMedias_searchBar);
        searchBar.setOnQueryTextListener(createNewQueryTextListener(searchBar));

        return view;
    }

    /**
     * Gets every checkable component
     * @param view actual view
     */
    private void initRegistry(View view) {
        animesCheckBox = view.findViewById(R.id.searchMedias_checkBox_animes);
        mangasCheckbox = view.findViewById(R.id.searchMedias_checkBox_mangas);
        moviesCheckbox = view.findViewById(R.id.searchMedias_checkBox_movies);
        showsCheckbox = view.findViewById(R.id.searchMedias_checkBox_shows);
        switchOrderByTitle = view.findViewById(R.id.searchMedias_switch);
    }

    /**
     * Creates a suitable {@link android.widget.SearchView.OnQueryTextListener} for the searchbar
     * @param searchBar searchbar
     * @return a suitable listener
     */
    private SearchView.OnQueryTextListener createNewQueryTextListener(SearchView searchBar) {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBar.clearFocus();
                results = new ArrayList<>();
                String textSearched = searchBar.getQuery().toString();

                if(switchOrderByTitle.isChecked())
                    getNonExcludedProviders()
                            .forEach(p -> p.searchForNbResults(textSearched,5)
                                    .enqueue(createNewCallback()));
                else
                    getNonExcludedProviders()
                            .forEach(p -> p.searchForNbResultsByScore(textSearched,5)
                                    .enqueue(createNewCallback()));
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        };
    }

    /**
     * Creates a suitable {@link Callback} for the asynchronous call to the api
     * @return suitable callback
     */
    private Callback<List<Media>> createNewCallback() {
        return new Callback<List<Media>>() {
            @Override
            public void onResponse(@NonNull Call<List<Media>> call, @NonNull Response<List<Media>> response) {
                if(response.body() != null) {
                    results.addAll(response.body());
                    adapter = new MediaAdapter(results, displayActionsListener,
                            switchOrderByTitle.isChecked() ?
                                    MediaAdapter.SortMode.TITLE : MediaAdapter.SortMode.SCORE);
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

    /**
     * Returns the providers that are not excluded by the user with the checkboxes
     * @return list of the remaining providers
     */
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
