package fr.efrei.android.blakkat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.SessionHelper;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import fr.efrei.android.blakkat.ui.views.DateAdapter;
import fr.efrei.android.blakkat.ui.views.TimelineAdapter;

public class TimelineFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        this.recyclerView = view.findViewById(R.id.recyclerView_global_timeline);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView.setAdapter(new TimelineAdapter(SessionHelper.get(getResources()
                .getString(R.string.user), UserRecord.class)));*/



        //TODO
        View view = inflater.inflate(R.layout.cell_global_timeline, container, false);

        UserRecord u = SessionHelper.get(getResources()
                .getString(R.string.user), UserRecord.class);

        Map<String, List<ProgressionRecord>> mediasSortedByDate = Select.from(ProgressionRecord.class)
                .where(Condition.prop("user_record").eq(String.valueOf(u.getId())))
                .orderBy("made DESC").list().stream().collect(Collectors.groupingBy((s -> toString().format("%tD", s.getMade()))));

        String date = mediasSortedByDate.keySet().toArray()[0].toString();

        this.recyclerView = view.findViewById(R.id.recyclerview_date);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));

        recyclerView.setAdapter(new DateAdapter(mediasSortedByDate.get(date), u));



        return view;
    }

}
