package fr.efrei.android.blakkat.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionHolder> {

    private ProgressionRecord progression;

    static class SuggestionHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        Button seenButton;
        View v;

        SuggestionHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title_progression);
            this.seenButton = vi.findViewById(R.id.title_progression);
            this.imageView = vi.findViewById(R.id.imageCard_progression);
        }
    }

    public SuggestionAdapter(ProgressionRecord progression) {
        this.progression = progression;

    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_episode_card, parent, false);
        return new SuggestionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionHolder holder, int position) {
        //TODO
    }
    @Override
    public int getItemCount() {
        //TODO
        //return this.progression.size();
        return 5;
    }
}

