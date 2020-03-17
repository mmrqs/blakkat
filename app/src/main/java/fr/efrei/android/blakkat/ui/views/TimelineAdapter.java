package fr.efrei.android.blakkat.ui.views;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.DateHelper;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineHolder> {
    private Map<String, List<ProgressionRecord>> mediasGroupedByDate;
    private String[] mediasKeys;
    private RecyclerView recyclerView;
    private UserRecord userRecord;

    static class TimelineHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View v;

        TimelineHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.date_timeline_tv);
        }
    }

    @SuppressLint("DefaultLocale")
    public TimelineAdapter(UserRecord u) {
        userRecord = u;
        mediasGroupedByDate = Select.from(ProgressionRecord.class)
                .where(Condition.prop("user_record")
                        .eq(String.valueOf(userRecord.getId())))
                .orderBy("made DESC").list().stream()
                .collect(Collectors.groupingBy(pr -> DateHelper.formatInternational(pr.getMade())));
        mediasKeys = mediasGroupedByDate.keySet()
                .stream()
                .sorted(Comparator.comparing(String::toString)
                        .reversed())
                .toArray(String[]::new);
    }

    @NonNull
    @Override
    public TimelineAdapter.TimelineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_global_timeline, parent, false);
        return new TimelineAdapter.TimelineHolder(v);
    }

    @Override
    public void onBindViewHolder(TimelineAdapter.TimelineHolder holder, int position) {
        String dateKey = mediasKeys[position];
        holder.textView.setText(DateHelper
                .format(mediasGroupedByDate.get(dateKey)
                        .get(0).getMade()));

        recyclerView = holder.v.findViewById(R.id.recyclerview_date);
        recyclerView.setLayoutManager(new LinearLayoutManager(holder.v.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new DateAdapter(mediasGroupedByDate.get(dateKey)));
    }

    @Override
    public int getItemCount() {
        return mediasKeys.length;
    }
}
