package fr.efrei.android.blakkat.view.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.DisplayActivity;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.consuming.providers.IAnimeProvider;
import fr.efrei.android.blakkat.consuming.providers.IMangaProvider;
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Anime;
import fr.efrei.android.blakkat.model.Manga;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {
    private List<Media> _myMedias;
    private Context _mContext;
    private Media _specificMedia;

    static class CardHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        View v;

        CardHolder(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title);
            this.imageView = vi.findViewById(R.id.imageCard);
        }
    }

    public CardAdapter(List<Media> myMedias, Context mContext) {
        _myMedias = myMedias;
        _mContext = mContext;
    }

    @Override
    public CardAdapter.CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cards, parent, false);
        CardHolder vh = new CardHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        _specificMedia = _myMedias.get(position);

        holder.textView.setText(_specificMedia.getTitle() +
                " – " + _specificMedia.getProviderHint());
        Picasso.with(holder.imageView.getContext())
                .load(_specificMedia
                        .getImageUrl()).centerCrop().fit()
                .into(holder.imageView);

        holder.v.setOnClickListener(v -> {
            if (_specificMedia.getProviderHint().equals("Anime")) {
                IAnimeProvider provider = KeeperFactory.getKeeper().getAnimeProvider();
                provider.getOne(_specificMedia.getId()).enqueue(createNewCallBackAnime());
            } else if (_specificMedia.getProviderHint().equals("Manga")) {
                IMangaProvider provider = KeeperFactory.getKeeper().getMangaProvider();
                provider.getOne(_specificMedia.getId()).enqueue(createNewCallBackManga());
            } else {
                Dispatch();
            }
        });
    }

    @Override
    public int getItemCount() {
        return _myMedias.size();
    }

    private Callback<Anime> createNewCallBackAnime() {
        return new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                _specificMedia = response.body();
                Dispatch();
            }
            @Override
            public void onFailure(Call<Anime> call, Throwable t) {
                Log.e("", t.toString());
            }
        };
    }

    private Callback<Manga> createNewCallBackManga() {
        return new Callback<Manga>() {
            @Override
            public void onResponse(Call<Manga> call, Response<Manga> response) {
                _specificMedia = response.body();
                Dispatch();
            }
            @Override
            public void onFailure(Call<Manga> call, Throwable t) {
                Log.e("", t.toString());
            }
        };
    }

    private void Dispatch() {
        Intent intent = new Intent(_mContext, DisplayActivity.class);
        intent.putExtra("MediaClicked", _specificMedia);
        _mContext.startActivity(intent);
    }
}
