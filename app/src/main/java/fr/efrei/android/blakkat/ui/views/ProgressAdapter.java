package fr.efrei.android.blakkat.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.*;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressHolder> {
    private UserRecord userRecord;
    private Media media;
    private MediaRecord mediaRecord;
    private List<ProgressionRecord> progress;
    private ProgressionRecord latest;

    static class ProgressHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button seenButton;
        View v;

        ProgressHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title_avancement);
            this.seenButton = vi.findViewById(R.id.check_display);
        }
    }

    public ProgressAdapter(Media media, UserRecord userRecord) {
        this.userRecord = userRecord;
        this.media = media;
        this.mediaRecord = MediaRecord.exists(media.getId(), media.getProviderHint());
        this.progress = media.getPossibleProgress();
    }

    @NonNull
    @Override
    public ProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_episode_card, parent, false);
        return new ProgressHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressHolder holder, int position) {
        ProgressionRecord subject = mediaRecord == null ? this.progress.get(position) :
                testExisting(this.progress.get(position));
        this.changeButtonText(subject, holder);

        holder.textView.setText(getLabelProgress(subject));
        holder.seenButton.setOnClickListener(view -> {
            mediaRecord = MediaRecord.exists(media.getId(), media.getProviderHint());
            latest = subject;

            List<ProgressionRecord> listPossibleSuggestions;

            if(mediaRecord == null) {
                mediaRecord = new MediaRecord(media);
                mediaRecord.save();
                listPossibleSuggestions = media.getPossibleSuggestion(userRecord, mediaRecord);
                subject.markViewed(userRecord, mediaRecord).save();

            } else {
                listPossibleSuggestions = media.getPossibleSuggestion(userRecord, mediaRecord);
                if (subject.isViewed())
                    subject.markViewed(userRecord, mediaRecord).save();
                else {
                    subject.delete();

                    latest = Select.from(ProgressionRecord.class)
                            .where(Condition.prop("user_record").eq(String.valueOf(userRecord.getId())),
                                    Condition.prop("media_record").eq(String.valueOf(mediaRecord.getId()))).orderBy("made DESC").list().get(0);
                }
            }
            this.changeButtonText(subject, holder);

            //SUGGESTION :
            //We watch if it exists a suggestion for this media :
            List<SuggestionRecord> r = SuggestionRecord.find(SuggestionRecord.class,
                    "user_record = ? and media_record = ?",
                    String.valueOf(userRecord.getId()),
                    String.valueOf(mediaRecord.getId()));
            if(!r.isEmpty()) r.get(0).delete();

            if(listPossibleSuggestions.size() > 1) {
                SuggestionRecord sr = new SuggestionRecord();
                sr.setUserRecord(userRecord);
                sr.setMediaRecord(mediaRecord);

                if(listPossibleSuggestions.indexOf(latest) < listPossibleSuggestions.size()-1) {
                    ProgressionRecord pp = listPossibleSuggestions.get(listPossibleSuggestions.indexOf(subject)+1);
                    pp.save();
                    sr.setProgressionRecord(pp);
                } else {
                    ProgressionRecord p = listPossibleSuggestions.get(0);
                    p.save();
                    sr.setProgressionRecord(p);
                }
                sr.save();
            }
        });
    }

    private ProgressionRecord testExisting(ProgressionRecord p) {
        ProgressionRecord test = ProgressionRecord
                .exists(userRecord, mediaRecord, p);

        return test == null ? p : test;
    }

    private void changeButtonText(ProgressionRecord subject, ProgressHolder holder) {
        holder.seenButton.setText(subject.isViewed() ?
                R.string.notviewed :
                R.string.viewed);
    }

    private String getLabelProgress(ProgressionRecord p) {
        String s = "";
        if (media.getProgressLevel1Label() != null)
            s += media.getProgressLevel1Label() + " " + p.getProgressLevel1();
        s += " " + media.getProgressLevel2Label() + " " + p.getProgressLevel2();
        return s;
    }

    @Override
    public int getItemCount() {
        return this.progress.size();
    }
}
