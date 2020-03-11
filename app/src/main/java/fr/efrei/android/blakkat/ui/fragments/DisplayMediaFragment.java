package fr.efrei.android.blakkat.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.MediaRecord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayMediaFragment extends Fragment {
    private MediaLoadedListener listener;
    private Button viewedToggleButton;
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

        viewedToggleButton = view.findViewById(R.id.viewed_toggle);
        viewedToggleButton.setOnClickListener(v -> {
            MediaRecord record = MediaRecord.exists(displayedMedia.getId(), displayedMedia.getProviderHint());
            if(record == null) {
                record = new MediaRecord(displayedMedia);
                record.save();
            } else {
                record.delete();
                record = null;
            }
            changeToggleViewedButtonContents(record == null ? null : record.getWatched());
        });

        view.findViewById(R.id.displayMedia_button_return)
                .setOnClickListener(v -> Objects.requireNonNull(this.getActivity())
                        .onBackPressed());

        MediaRecord record = MediaRecord.exists(displayedMedia.getId(),
                displayedMedia.getProviderHint());
        changeToggleViewedButtonContents(record == null ? null : record.getWatched());
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

    /**
     * Changes the content of the button
     * @param date {@link Date} on which the user has seen the media null if not seen
     */
    private void changeToggleViewedButtonContents(Date date) {
        viewedToggleButton.setText(date == null ?
                getResources().getString(R.string.notviewed) :
                String.format(getResources()
                        .getString(R.string.viewed),
                        date));
    }

    public interface MediaLoadedListener {
        void onMediaLoaded(Media media);
    }
}