package fr.efrei.android.blakkat.view.Adapters;

import android.content.Context;
import android.content.Intent;
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
import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {
    private List<Media> medias;
    private Context context;

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
        medias = myMedias;
        context = mContext;
    }

    @Override
    public CardAdapter.CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cards, parent, false);
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        holder.textView.setText(medias.get(position).getTitle() +
                " – " + medias.get(position).getProviderHint());

        if(medias.get(position).getImageUrl().isEmpty())
            holder.imageView.setImageResource(R.drawable.question_mark);
        else
            Picasso.with(holder.imageView.getContext())
                    .load(medias.get(position).getImageUrl())
                    .placeholder(R.drawable.question_mark)
                    .error(R.drawable.question_mark)
                    .centerCrop().fit()
                    .into(holder.imageView);

        holder.v.setOnClickListener(v -> KeeperFactory.getKeeper()
                .getProviderFor(medias.get(position))
                .getOne(medias.get(position).getId())
                .enqueue(createNewCallBack(position)));
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    private Callback<Media> createNewCallBack(int position) {
        return new Callback<Media>() {
            @Override
            public void onResponse(Call<Media> call, Response<Media> response) {
                medias.set(position, response.body());
                dispatch(position);
            }
            @Override
            public void onFailure(Call<Media> call, Throwable t) {
                Log.e("", t.toString());
            }
        };
    }

    private void dispatch(int position) {
        Intent intent = new Intent(context, DisplayActivity.class);
        intent.putExtra("MediaClicked", medias.get(position));
        context.startActivity(intent);
    }
}
