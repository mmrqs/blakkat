package fr.efrei.android.blakkat.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.MediaRecord;
import fr.efrei.android.blakkat.ui.views.MediaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewedMediasFragment extends Fragment {
    private ArrayList<Media> seen;
    private MediaAdapter.DisplayActionsListener listener;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (MediaAdapter.DisplayActionsListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewed_medias, container, false);

        this.seen = new ArrayList<>();
        this.recyclerView = view.findViewById(R.id.viewedMedias_recyclerView_viewed);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MediaRecord.findAll(MediaRecord.class)
                .forEachRemaining(mr -> mr.getCorresponding()
                        .enqueue(createNewCallback()));

        return view;
    }

    private Callback<Media> createNewCallback() {
        return new Callback<Media>() {
            @Override
            public void onResponse(@NonNull Call<Media> call, @NonNull Response<Media> response) {
                if(response.body() != null) {
                    seen.add(response.body());
                    recyclerView.setAdapter(new MediaAdapter(seen, listener,
                            MediaAdapter.SortMode.TITLE));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Media> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e("Err", t.getLocalizedMessage());
            }
        };
    }

}
