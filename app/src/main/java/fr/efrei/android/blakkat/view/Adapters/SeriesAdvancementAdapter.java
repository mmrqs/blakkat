package fr.efrei.android.blakkat.view.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;

import fr.efrei.android.blakkat.R;

public class SeriesAdvancementAdapter extends RecyclerView.Adapter<SeriesAdvancementAdapter.CardHolderSerie> {
    private ArrayList<String> _displaySeasons;
    private Context context;

    static class CardHolderSerie extends RecyclerView.ViewHolder {
        TextView textView;
        Button seenButton;
        View v;

        CardHolderSerie(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title_avancement);
            this.seenButton = vi.findViewById(R.id.check_display);
        }
    }

    public SeriesAdvancementAdapter(ArrayList<String> displaySeasons, Context mContext) {
        _displaySeasons = displaySeasons;
        context = mContext;
    }

    @NonNull
    @Override
    public SeriesAdvancementAdapter.CardHolderSerie onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_episode_card, parent, false);
        return new SeriesAdvancementAdapter.CardHolderSerie(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SeriesAdvancementAdapter.CardHolderSerie holder, int position) {
        holder.textView.setText(_displaySeasons.get(position));
        holder.seenButton.setOnClickListener(view -> {
           
        });
        /*holder.v.setOnClickListener(v -> KeeperFactory.getKeeper()
                .getProviderFor(medias.get(position))
                .getOne(medias.get(position).getId())
                .enqueue(createNewCallBack(position)));*/
    }

    @Override
    public int getItemCount() {
        return _displaySeasons.size();
    }

}
