package fr.efrei.android.blakkat.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.*;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressHolder> {
    private UserRecord userRecord;
    private Media media;
    private MediaRecord mediaRecord;
    private List<ProgressionRecord> progress;

    private String viewed;
    private String notViewed;

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

    public ProgressAdapter(Media media, UserRecord userRecord, MediaRecord mediaRecord) {
        this.userRecord = userRecord;
        this.media = media;
        this.mediaRecord = mediaRecord;
        this.progress = media.getPossibleProgress();
    }

    @NonNull
    @Override
    public ProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card_progress, parent, false);

        this.viewed = v.getContext().getResources()
                .getString(R.string.viewed);
        this.notViewed = v.getContext().getResources()
                .getString(R.string.not_viewed);

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

            if(mediaRecord == null) {
                mediaRecord = new MediaRecord(media);
                mediaRecord.save();
                subject.markViewed(userRecord, mediaRecord).save();
            } else {
                if (subject.isViewed())
                    subject.markViewed(userRecord, mediaRecord).save();
                else
                    subject.delete();
            }

            this.changeButtonText(subject, holder);
        });
    }

    private ProgressionRecord testExisting(ProgressionRecord p) {
        ProgressionRecord test = ProgressionRecord
                .exists(userRecord, mediaRecord, p);

        return test == null ? p : test;
    }

    private void changeButtonText(ProgressionRecord subject, ProgressHolder holder) {
        holder.seenButton.setText(subject.isViewed() ?
                this.notViewed :
                String.format(viewed, subject.getMade()));
    }

    private String getLabelProgress(ProgressionRecord p) {
        String s = media.getProgressLevel1Label() + " " + p.getProgressLevel1();
        if (media.getProgressLevel2Label() != null)
            s += " " + media.getProgressLevel2Label() + " " + p.getProgressLevel2();
        return s;
    }

    @Override
    public int getItemCount() {
        return this.progress.size();
    }
}
