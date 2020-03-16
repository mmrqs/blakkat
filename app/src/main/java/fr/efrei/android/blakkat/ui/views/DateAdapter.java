package fr.efrei.android.blakkat.ui.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder> {
    private List<ProgressionRecord> mediasSorted;
    private UserRecord userRecord;

    static class DateHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        View v;

        DateHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title);
            this.imageView = vi.findViewById(R.id.imageCard);
        }
    }

    public DateAdapter(List<ProgressionRecord> progressionsList, UserRecord u) {
        mediasSorted = progressionsList;
        userRecord = u;
    }

    @NonNull
    @Override
    public DateAdapter.DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card_media, parent, false);
        return new DateAdapter.DateHolder(v);
    }

    @Override
    public void onBindViewHolder(DateAdapter.DateHolder holder, int position) {

        holder.textView.setText(mediasSorted.get(position).getMediaRecord().getTitle() + " - " +
                getLabelProgress(mediasSorted.get(position)));


        if(mediasSorted.get(position).getMediaRecord().getUrl().isEmpty())
            holder.imageView.setImageResource(R.drawable.question_mark);
        else
            Picasso.with(holder.imageView.getContext())
                    .load(mediasSorted.get(position).getMediaRecord().getUrl())
                    .placeholder(R.drawable.question_mark)
                    .error(R.drawable.question_mark)
                    .centerCrop().fit()
                    .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mediasSorted.size();
    }

    private String getLabelProgress(ProgressionRecord p) {
        String s = "";
        if (p.getProgressLevel1() != 0)
            s += "Season : " + p.getProgressLevel1();
        s += " Episode : " + p.getProgressLevel2();
        return s;
    }
}
