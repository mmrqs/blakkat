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
            this.textView = vi.findViewById(R.id.title_timeline);
            this.imageView = vi.findViewById(R.id.imageCard_timeline);
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
                .inflate(R.layout.cell_card_timeline, parent, false);

        return new DateAdapter.DateHolder(v);
    }

    @Override
    public void onBindViewHolder(DateAdapter.DateHolder holder, int position) {

        String title = mediasSorted.get(position).getMediaRecord().getTitle();
        if (title.length() > 15) {
            title = title.substring(0, 15);
            title += "...";
        }

        holder.textView.setText(title + " - " +
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
            s += "S" + p.getProgressLevel1();
        s += " Ep" + p.getProgressLevel2();
        return s;
    }
}
