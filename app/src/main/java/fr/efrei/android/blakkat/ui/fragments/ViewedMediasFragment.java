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
import java.util.HashSet;
import java.util.Set;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.helpers.SessionHelper;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import fr.efrei.android.blakkat.ui.views.MediaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This {@link Fragment} will show the list of {@link Media} seen by the user
 */
public class ViewedMediasFragment extends Fragment {
    private ArrayList<Media> seen;
    private MediaAdapter.DisplayActionsListener listener;
    private RecyclerView recyclerView;

    /**
     * {@inheritDoc}
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (MediaAdapter.DisplayActionsListener) context;
    }

    /**
     * {@inheritDoc}
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewed_medias, container, false);

        UserRecord u = SessionHelper.get(getResources().getString(R.string.user), UserRecord.class);
        this.seen = new ArrayList<>();
        this.recyclerView = view.findViewById(R.id.viewedMedias_recyclerView_viewed);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        Set<Long> ids = new HashSet<>();

        ProgressionRecord.findAll(ProgressionRecord.class)
                .forEachRemaining(pr -> {
                    if(pr.getMade() != null &&
                            pr.getUserRecord().getId().equals(u.getId()) &&
                            !ids.contains(pr.getMediaRecord().getId())) {
                        ids.add(pr.getMediaRecord().getId());
                        pr.getMediaRecord().getCorrespondingMedia()
                                .enqueue(createNewCallback());
                    }
                });

        return view;
    }

    /**
     * Creates a suitable {@link Callback} for the asynchronous request to the APIs
     * @return suitable {@link Callback}
     */
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
