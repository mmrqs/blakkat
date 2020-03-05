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
import fr.efrei.android.blakkat.model.IMedia;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {
    private List<IMedia> _myMedias;
    private Context _mContext;

    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public View v;

        public CardHolder(View vi) {
            super(vi);
            v = vi;
            textView = vi.findViewById(R.id.title);
            imageView = vi.findViewById(R.id.imageCard);

        }
    }

    public CardAdapter(List<IMedia> myMedias, Context mContext) {
        _myMedias = myMedias;
        _mContext = mContext;
    }

    @Override
    public CardAdapter.CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cards, parent, false);
        CardHolder vh = new CardHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        holder.textView.setText(_myMedias.get(position).getTitle());
        Picasso.with(holder.imageView.getContext()).load(_myMedias.get(position).getImageUrl()).centerCrop().fit().into(holder.imageView);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent=new Intent(_mContext, DisplayActivity.class);
                intent.putExtra("MediaClicked", (Parcelable) _myMedias.get(position));
                _mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _myMedias.size();
    }
}
