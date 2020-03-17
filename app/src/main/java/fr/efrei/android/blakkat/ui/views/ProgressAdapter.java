package fr.efrei.android.blakkat.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.*;

/**
 * Displays a list of the progress made on a {@link Media}
 */
public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressHolder> {
    private UserRecord userRecord;
    private Media media;
    private MediaRecord mediaRecord;
    private List<ProgressionRecord> progress;
    private ProgressionRecord latest;

    static class ProgressHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton seenButton;
        View v;

        ProgressHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title_avancement);
            this.seenButton = vi.findViewById(R.id.check_display);
        }
    }

    /**
     * {@inheritDoc}
     * @param media
     * @param userRecord
     * @param mediaRecord
     */
    public ProgressAdapter(Media media, UserRecord userRecord, MediaRecord mediaRecord) {
        this.userRecord = userRecord;
        this.media = media;
        this.mediaRecord = mediaRecord;
        this.progress = media.getPossibleProgress();
    }

    /**
     * {@inheritDoc}
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card_progress, parent, false);

        return new ProgressHolder(v);
    }

    /**
     * {@inheritDoc}
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ProgressHolder holder, int position) {
        ProgressionRecord subject = mediaRecord == null ? this.progress.get(position) :
                testExisting(this.progress.get(position));
        this.changeButtonView(subject, holder);

        holder.textView.setText(getLabelProgress(subject));
        holder.seenButton.setOnClickListener(view -> {
            mediaRecord = MediaRecord.exists(media.getId(), media.getProviderHint());
            latest = subject;

            //if the media record is null we create it with a new progress record
            if(mediaRecord == null) {
                mediaRecord = new MediaRecord(media);
                mediaRecord.save();
                subject.markViewed(userRecord, mediaRecord).save();
            } else {
                //if the progress was made we delete it from database ; otherwise we save it
                if (subject.isViewed())
                    subject.markViewed(userRecord, mediaRecord).save();
                else {
                    subject.delete();

                    //finding the latest progress made on this media
                    List<ProgressionRecord> madeProgress = Select.from(ProgressionRecord.class)
                            .where(Condition.prop("user_record")
                                            .eq(String.valueOf(userRecord.getId())),
                                    Condition.prop("media_record")
                                            .eq(String.valueOf(mediaRecord.getId())))
                            .orderBy("made DESC").list();
                    latest = madeProgress.size() > 0 ? madeProgress.get(0) : null;
                }
            }
            this.changeButtonView(subject, holder);

            List<ProgressionRecord> listPossibleSuggestions = media
                    .getPossibleSuggestions(userRecord, mediaRecord);

            //SUGGESTION :
            //We watch if it exists a suggestion for this media :
            List<SuggestionRecord> r = SuggestionRecord.find(SuggestionRecord.class,
                    "user_record = ? and media_record = ?",
                    userRecord.getId().toString(),
                    mediaRecord.getId().toString());

            if(!r.isEmpty())
                r.get(0).delete();

            if(listPossibleSuggestions.size() > 0) {
                SuggestionRecord sr = new SuggestionRecord(userRecord, mediaRecord);

                ProgressionRecord p = listPossibleSuggestions.get(0);
                p.save();
                sr.setProgressionRecord(p);
                sr.save();
            }
        });
    }

    /**
     * Tests the existence of a progression record in database
     * @param p whose existence will be tested
     * @return true if it exists
     */
    private ProgressionRecord testExisting(ProgressionRecord p) {
        ProgressionRecord test = ProgressionRecord
                .exists(userRecord, mediaRecord, p);

        return test == null ? p : test;
    }

    /**
     * Changes the content of the button in accordance with the progress it represents
     * @param subject progress
     * @param holder view holding the button
     */
    private void changeButtonView(ProgressionRecord subject, ProgressHolder holder) {
        holder.seenButton.setImageResource(subject.isViewed() ?
                R.drawable.ic_action_check_notviewed:
                R.drawable.ic_action_check_ok);
    }

    /**
     * Creates a label for the progression
     * @param p subject
     * @return a {@link String} that represents the infos of the progress
     */
    private String getLabelProgress(ProgressionRecord p) {
        String s = "";
        if (media.getProgressLevel1Label() != null)
            s += media.getProgressLevel1Label() + " " + p.getProgressLevel1();
        s += " " + media.getProgressLevel2Label() + " " + p.getProgressLevel2();
        return s;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int getItemCount() {
        return this.progress.size();
    }
}
