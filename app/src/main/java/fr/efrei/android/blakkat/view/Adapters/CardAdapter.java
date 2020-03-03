package fr.efrei.android.blakkat.view.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.IMedia;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {
    private List<IMedia> _myMedias;

    public static class CardHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CardHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.title);
        }
    }

    public CardAdapter(List<IMedia> myMedias) {
        _myMedias = myMedias;
    }

    @Override
    public CardAdapter.CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cards, parent, false);

        CardHolder vh = new CardHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        holder.textView.setText(_myMedias.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return _myMedias.size();
    }
}
