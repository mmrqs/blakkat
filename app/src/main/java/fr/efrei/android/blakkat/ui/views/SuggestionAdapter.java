package fr.efrei.android.blakkat.ui.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.MediaRecord;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.SuggestionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        this.suggestions = SuggestionRecord.find(SuggestionRecord.class,"user_record = ?", String.valueOf(userRecord.getId()));
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

        holder.seenButton.setOnClickListener(view -> {
            SuggestionRecord actualSuggestion = suggestions.get(position);

            KeeperFactory.getKeeper().getProviderFor(actualSuggestion
                    .getMediaRecord().getType()).getOne(actualSuggestion.getMediaRecord().getIdentifier()).enqueue(createNewCallback(holder, position));
        });
    }

    private Callback<Media> createNewCallback(@NonNull SuggestionHolder holder, int position) {
        return new Callback<Media>() {
            @Override
            public void onResponse(Call<Media> call, Response<Media> response) {
                if (response.body() != null) {
                    ProgressionRecord actualProgression =
                            new ProgressionRecord(suggestions.get(position).getProgressionRecord().getProgressLevel1(),
                                    suggestions.get(position).getProgressionRecord().getProgressLevel2());

                    Media media = response.body();
                    MediaRecord mr = MediaRecord.exists(media.getId(), media.getProviderHint());
                    List<ProgressionRecord> listPossibleSuggestions = media.getPossibleSuggestion(userRecord,mr);

                    suggestions.get(position).getProgressionRecord().markViewed(userRecord, mr).save();

                    suggestions.get(position).delete();

                    if (listPossibleSuggestions.size() > 0) {
                        SuggestionRecord sr = new SuggestionRecord();
                        sr.setUserRecord(userRecord);
                        sr.setMediaRecord(suggestions.get(position).getMediaRecord());

                        if (listPossibleSuggestions.indexOf(actualProgression) < listPossibleSuggestions.size() - 1) {
                            ProgressionRecord pp = listPossibleSuggestions.get(listPossibleSuggestions.indexOf(actualProgression) + 1);
                            pp.save();
                            sr.setProgressionRecord(pp);
                        } else {
                            ProgressionRecord p = listPossibleSuggestions.get(0);
                            p.save();
                            sr.setProgressionRecord(p);
                        }
                        sr.save();
                        Collections.replaceAll(suggestions, suggestions.get(position), sr);
                        onBindViewHolder(holder, position);
                    }
                    else {
                        suggestions.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), suggestions.size());
                    }
                }
            }
            @Override
            public void onFailure(Call<Media> call, Throwable t) {
                t.printStackTrace();
                Log.e("Err", t.getLocalizedMessage());
            }
        };
    }

    @Override
    public int getItemCount() {
        return this.suggestions.size();
    }

    private String getLabelProgress(SuggestionRecord p) {
        String s = "";
        if (p.getProgressionRecord().getProgressLevel1() != 0)
            s += "Season : " + p.getProgressionRecord().getProgressLevel1();
        s += " Episode : " + p.getProgressionRecord().getProgressLevel2();
        return s;
    }
}

