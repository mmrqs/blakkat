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
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.helpers.DateHelper;
import fr.efrei.android.blakkat.helpers.SessionHelper;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.RatingRecord;
import fr.efrei.android.blakkat.model.Record.MediaRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import fr.efrei.android.blakkat.ui.views.ProgressAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayMediaFragment extends Fragment {
    private UserRecord userRecord;
    private MediaRecord mediaRecord;
    private RatingRecord ratingRecord;

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
     * @return TODO
     */
    private Callback<Media> createNewCallBack() {
        return new Callback<Media>() {
            @Override
            public void onResponse(@NonNull Call<Media> call, @NonNull Response<Media> response) {
                displayedMedia = response.body();
                listener.onMediaLoaded(displayedMedia);
                refreshGraphicalComponent();
            }

            @Override
            public void onFailure(@NonNull Call<Media> call, @NonNull Throwable t) {
                Log.e("", t.toString());
            }
        };
    }

    /**
     * Displays the {@link Media} infos
     */
    private void initGraphicalComponents() {
        userRecord = SessionHelper.get(getResources()
                .getString(R.string.user), UserRecord.class);
        mediaRecord = MediaRecord.exists(displayedMedia.getId(),
                displayedMedia.getProviderHint());

        initImageView();
        initTextViews();
        initRatingBar();
        initRecyclerProgress();
    }

    private void initImageView() {
        ImageView imageView = view.findViewById(R.id.imageCard_displayActivity);
        Picasso.with(imageView.getContext())
                .load(displayedMedia.getImageUrl().isEmpty() ? null :
                        displayedMedia.getImageUrl())
                .placeholder(R.drawable.question_mark)
                .error(R.drawable.question_mark)
                .centerCrop().fit()
                .into(imageView);
    }

    private void initTextViews() {
        ((TextView)view.findViewById(R.id.titleDisplay))
                .setText(displayedMedia.getTitle());

        ((TextView)view.findViewById(R.id.time))
                .setText(DateHelper.format(displayedMedia.getReleaseDate()));

        ((TextView)view.findViewById(R.id.genre))
                .setText(getResources().getString(R.string.loading_text));

        ((TextView)view.findViewById(R.id.SynopsisContent_Display))
                .setText(String.format(getResources().getString(R.string.loading_text),
                        displayedMedia.getSynopsis()));
    }

    private void initRecyclerProgress() {
        RecyclerView.Adapter adapter = new ProgressAdapter(displayedMedia, userRecord, mediaRecord);

        RecyclerView recyclerProgress = view.findViewById(R.id.displayFragment_recyclerView);
        recyclerProgress.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerProgress.setLayoutManager(layoutManager);

        recyclerProgress.setAdapter(adapter);
    }

    private void initRatingBar() {
        RatingBar bar = view.findViewById(R.id.displayMedia_ratingBar);

        if(mediaRecord != null) {
            ratingRecord = RatingRecord.exists(userRecord, mediaRecord);
            if(ratingRecord != null)
                bar.setRating(ratingRecord.getRating());
        }

        bar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(mediaRecord == null) {
                mediaRecord = new MediaRecord(displayedMedia);
                mediaRecord.save();
            }

            ratingRecord = RatingRecord.exists(userRecord, mediaRecord);
            if(ratingRecord != null)
                ratingRecord.setRating((int) rating);
            else
                ratingRecord = new RatingRecord((int)rating, userRecord, mediaRecord);
            ratingRecord.save();
        });
    }

    /**
     * Refreshes the infos on the displayed {@link Media} after it finishes loading
     */
    private void refreshGraphicalComponent() {
        ((TextView)view.findViewById(R.id.genre))
                .setText(displayedMedia.getGenres() == null ? "" :
                        displayedMedia.getGenres().toString());

        ((TextView)view.findViewById(R.id.SynopsisContent_Display))
                .setText(displayedMedia.getSynopsis());
    }

    public interface MediaLoadedListener {
        void onMediaLoaded(Media media);
    }
}