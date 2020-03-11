package fr.efrei.android.blakkat.ui.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.Toaster;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {
    private List<Media> medias;
    private DisplayActionsListener displayActionsListener;

    static class CardHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        View v;

        CardHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title);
            this.imageView = vi.findViewById(R.id.imageCard);
        }
    }

    public CardAdapter(List<Media> medias, DisplayActionsListener displayActionsListener) {
        this.medias = medias;
        this.displayActionsListener = displayActionsListener;
    }

    @Override
    public CardAdapter.CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cell_cards, parent, false);
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
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
}
