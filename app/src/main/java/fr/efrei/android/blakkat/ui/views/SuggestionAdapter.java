package fr.efrei.android.blakkat.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Record.SuggestionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionHolder> {

    private List<SuggestionRecord> suggestions;
    private UserRecord userRecord;

    static class SuggestionHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        Button seenButton;
        View v;

        SuggestionHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title_progression);
            this.seenButton = vi.findViewById(R.id.seen_progression);
            this.imageView = vi.findViewById(R.id.imageCard_progression);
        }
    }

    public SuggestionAdapter(UserRecord userRecord) {
        this.userRecord = userRecord;
        this.suggestions = SuggestionRecord.listAll(SuggestionRecord.class);

    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_progression_card, parent, false);
        return new SuggestionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionHolder holder, int position) {
        holder.textView.setText(suggestions.get(position).getMediaRecord().getTitle() +
                " – " + getLabelProgress(suggestions.get(position)));

        if(suggestions.get(position).getMediaRecord().getUrl().isEmpty())
            holder.imageView.setImageResource(R.drawable.question_mark);
        else
            Picasso.with(holder.imageView.getContext())
                    .load(suggestions.get(position).getMediaRecord().getUrl())
                    .placeholder(R.drawable.question_mark)
                    .error(R.drawable.question_mark)
                    .centerCrop().fit()
                    .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return this.suggestions.size();
    }

    private String getLabelProgress(SuggestionRecord p) {
        String s = "";
        System.out.println(p.getProgressionRecord().getProgressLevel1());
        System.out.println(p.getProgressionRecord().getProgressLevel2());
        if (p.getProgressionRecord().getProgressLevel1() != null)
            s += "Saison : " + p.getProgressionRecord().getProgressLevel1();
        s += " Episode : " + p.getProgressionRecord().getProgressLevel2();

        System.out.println(s);
        return s;
    }
}

