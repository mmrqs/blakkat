package fr.efrei.android.blakkat.view.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {
    private List<Media> _myMedias;
    private Context _mContext;

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
        holder.textView.setText(_myMedias.get(position).getTitle() +
                " – " + _myMedias.get(position).getProviderHint());
        Picasso.with(holder.imageView.getContext())
                .load(_myMedias.get(position)
                        .getImageUrl()).centerCrop().fit()
                .into(holder.imageView);

        holder.v.setOnClickListener(v -> {
            Intent intent = new Intent(_mContext, DisplayActivity.class);
            intent.putExtra("MediaClicked", (Parcelable)_myMedias.get(position));
            _mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return _myMedias.size();
    }
}
