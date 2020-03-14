package fr.efrei.android.blakkat.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Record.SuggestionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import fr.efrei.android.blakkat.ui.views.SuggestionAdapter;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private List<SuggestionRecord> suggestions;
    //TODO
    //private MediaAdapter.DisplayActionsListener listener;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //this.listener = (MediaAdapter.DisplayActionsListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.suggestions = SuggestionRecord.listAll(SuggestionRecord.class);

        this.recyclerView = view.findViewById(R.id.recyclerView_home_suggestion);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerView.setAdapter(new SuggestionAdapter(UserRecord.find(UserRecord.class, "pseudo = ?",
                getContext().getSharedPreferences(getResources().getString(R.string.user), MODE_PRIVATE)
                        .getString(getResources().getString(R.string.user), null)).get(0)));

        return view;
    }

}
