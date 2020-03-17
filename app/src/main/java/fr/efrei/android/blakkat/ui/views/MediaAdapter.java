package fr.efrei.android.blakkat.ui.views;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;

/**
 * Shows a list of {@link Media}
 */
public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaHolder> {
    private List<Media> medias;
    private DisplayActionsListener displayActionsListener;

    static class MediaHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        View v;

        MediaHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title);
            this.imageView = vi.findViewById(R.id.imageCard);
        }
    }

    /**
     * Constructor
     * @param medias list to display
     * @param displayActionsListener parent activity
     * @param mode sorts results by title or public score
     */
    public MediaAdapter(List<Media> medias, DisplayActionsListener displayActionsListener, SortMode mode) {
        if(mode == SortMode.TITLE) {
            this.medias = medias.stream().sorted(Comparator.comparing(Media::getTitle))
                    .collect(Collectors.toList());
        } else {
            this.medias = medias.stream().sorted(Comparator.comparing(Media::getPublicScore)
                    .reversed())
                    .collect(Collectors.toList());
        }
        this.displayActionsListener = displayActionsListener;
    }

    /**
     * {@inheritDoc}
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MediaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card_media, parent, false);
        return new MediaHolder(v);
    }

    /**
     * {@inheritDoc}
     * @param holder
     * @param position
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MediaHolder holder, int position) {
        holder.textView.setText(medias.get(position).getTitle() +
                " – " + medias.get(position).getProviderHint());

        if(medias.get(position).getImageUrl().isEmpty())
            holder.imageView.setImageResource(R.drawable.question_mark);
        else
            Picasso.with(holder.imageView.getContext())
                    .load(medias.get(position).getImageUrl())
                    .placeholder(R.drawable.question_mark)
                    .error(R.drawable.question_mark)
                    .centerCrop().fit()
                    .into(holder.imageView);

        holder.v.setOnClickListener(v -> displayActionsListener
                .onMediaChosen(medias.get(position)));
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int getItemCount() {
        return medias.size();
    }

    /**
     * Allows communication with the parent activity to request the display of a chosen media
     */
    public interface DisplayActionsListener {
        void onMediaChosen(Media media);
    }

    /**
     * Sorting mode for the list
     */
    public enum SortMode {
        TITLE,
        SCORE
    }
}
