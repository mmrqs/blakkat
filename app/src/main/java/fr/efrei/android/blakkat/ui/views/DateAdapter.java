package fr.efrei.android.blakkat.ui.views;

import android.annotation.SuppressLint;
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
    private List<ProgressionRecord> mediasGrouped;

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

    public DateAdapter(List<ProgressionRecord> progressionsList) {
        mediasGrouped = progressionsList;
    }

    @NonNull
    @Override
    public DateAdapter.DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_card_timeline, parent, false);

        return new DateAdapter.DateHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DateAdapter.DateHolder holder, int position) {
        String title = mediasGrouped.get(position).getMediaRecord().getTitle();
        if (title.length() > 15) {
            title = title.substring(0, 15);
            title += "...";
        }

        holder.textView.setText(title + " - " +
                getLabelProgress(mediasGrouped.get(position)));

        if(mediasGrouped.get(position).getMediaRecord().getUrl().isEmpty())
            holder.imageView.setImageResource(R.drawable.question_mark);
        else
            Picasso.with(holder.imageView.getContext())
                    .load(mediasGrouped.get(position).getMediaRecord().getUrl())
                    .placeholder(R.drawable.question_mark)
                    .error(R.drawable.question_mark)
                    .centerCrop().fit()
                    .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mediasGrouped.size();
    }

    private String getLabelProgress(ProgressionRecord p) {
        String s = "";
        if (p.getProgressLevel1() != 0)
            s += "S" + p.getProgressLevel1();
        s += " Ep" + p.getProgressLevel2();
        return s;
    }
}
