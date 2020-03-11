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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.ui.views.CardAdvancementAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayMediaFragment extends Fragment {
    private MediaLoadedListener listener;
    private Media displayedMedia;
    private View view;

    public DisplayMediaFragment(Media displayedMedia) {
        this.displayedMedia = displayedMedia;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (MediaLoadedListener) context;
            loadMedia();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MediaLoadedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_display_media, container, false);

        assert displayedMedia != null;
        initGraphicalComponents();
        return view;
    }

    /**
     * Loads the informations of the media
     */
    private void loadMedia() {
        KeeperFactory.getKeeper()
                .getProviderFor(displayedMedia)
                .getOne(displayedMedia.getId())
                .enqueue(createNewCallBack());
    }

    /**
     * Creates a {@link Callback} to the request of the displayed media
     * @return
     */
    private Callback<Media> createNewCallBack() {
        return new Callback<Media>() {
            @Override
            public void onResponse(Call<Media> call, Response<Media> response) {
                displayedMedia = response.body();
                listener.onMediaLoaded(displayedMedia);
                refreshGraphicalComponent();
            }
            @Override
            public void onFailure(Call<Media> call, Throwable t) {
                Log.e("", t.toString());
            }
        };
    }

    /**
     * Displays the {@link Media} infos
     */
    private void initGraphicalComponents() {
        ((TextView)view.findViewById(R.id.titleDisplay))
                .setText(displayedMedia.getTitle());

        ImageView imageView = view.findViewById(R.id.imageCard_displayActivity);
        Picasso.with(imageView.getContext())
                .load(displayedMedia.getImageUrl())
                .placeholder(R.drawable.question_mark)
                .error(R.drawable.question_mark)
                .centerCrop().fit()
                .into(imageView);

        ((TextView)view.findViewById(R.id.time))
                .setText(displayedMedia.getReleaseDate().toString());

        ((TextView)view.findViewById(R.id.genre))
                .setText(getResources().getString(R.string.loading_text));

        ((TextView)view.findViewById(R.id.SynopsisContent_Display))
                .setText(displayedMedia.getSynopsis() +
                        getResources().getString(R.string.loading_text));

        view.findViewById(R.id.displayMedia_button_return)
                .setOnClickListener(v -> Objects.requireNonNull(this.getActivity())
                        .onBackPressed());

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView_ActivityDisplay);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = null;
        // Seasons cards :
        switch (displayedMedia.getProviderHint()) {
            case "Show":
                mAdapter = new CardAdvancementAdapter(seasonsFormatter(displayedMedia.getSeasons()), DisplayMediaFragment.this.getContext(),
                        displayedMedia);
                break;
            case "Anime":
            case "Manga":
                mAdapter = new CardAdvancementAdapter(mangaAnimeFormatter(displayedMedia.getSeasons()),DisplayMediaFragment.this.getContext(),
                        displayedMedia);
                break;
            case "Movie":
                mAdapter = new CardAdvancementAdapter(new ArrayList<String>() {{add(displayedMedia.getTitle());}},
                        DisplayMediaFragment.this.getContext(), displayedMedia);
            default :
                break;
        }

        recyclerView.setAdapter(mAdapter);

    }

    /**
     * Refreshes the infos on the displayed {@link Media} after it finishes loading
     */
    private void refreshGraphicalComponent() {
        ((TextView)view.findViewById(R.id.genre))
                .setText(displayedMedia.getGenres().toString());

        ((TextView)view.findViewById(R.id.SynopsisContent_Display))
                .setText(displayedMedia.getSynopsis());
    }

    public interface MediaLoadedListener {
        void onMediaLoaded(Media media);
    }

    public ArrayList<String> seasonsFormatter(HashMap<Integer, Integer> h) {
        ArrayList<String> a = new ArrayList<>();
        for (int i : h.keySet()) {
            for (int j = 1; j <= h.get(i) ; j++) {
                a.add("Saison " + i + " Episode " + j);
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