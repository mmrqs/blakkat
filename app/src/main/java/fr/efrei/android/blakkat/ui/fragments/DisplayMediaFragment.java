package fr.efrei.android.blakkat.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.MediaRecord;

public class DisplayMediaFragment extends Fragment {
    private Button viewedToggleButton;
    private Media displayedMedia;

    public DisplayMediaFragment(Media displayedMedia) {
        this.displayedMedia = displayedMedia;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_media, container, false);

        assert displayedMedia != null;

        initGraphicalComponents(view);

        return view;
    }

    private void initGraphicalComponents(View view) {
        ((TextView)view.findViewById(R.id.titleDisplay))
                .setText(displayedMedia.getTitle());

        ImageView imageView = view.findViewById(R.id.imageCard_displayActivity);
        Picasso.with(imageView.getContext())
                .load(displayedMedia.getImageUrl())
                .centerCrop().fit().into(imageView);

        ((TextView)view.findViewById(R.id.time))
                .setText(displayedMedia.getReleaseDate().toString());

        ((TextView)view.findViewById(R.id.genre))
                .setText(displayedMedia.getGenres().toString());

        ((TextView)view.findViewById(R.id.SynopsisContent_Display))
                .setText(displayedMedia.getSynopsis());

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
            changeToggleViewedButtonContents(record);
        });

        view.findViewById(R.id.displayMedia_button_return)
                .setOnClickListener(v -> Objects.requireNonNull(this.getActivity())
                        .onBackPressed());

        this.changeToggleViewedButtonContents(MediaRecord
                .exists(displayedMedia.getId(), displayedMedia.getProviderHint()));
    }

    private void changeToggleViewedButtonContents(MediaRecord mr) {
        viewedToggleButton.setText(mr == null ?
                getResources().getString(R.string.notviewed) :
                String.format(getResources()
                        .getString(R.string.viewed),
                        mr.getWatched()));
    }
}