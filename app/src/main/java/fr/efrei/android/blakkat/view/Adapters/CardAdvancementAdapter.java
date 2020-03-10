package fr.efrei.android.blakkat.view.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.efrei.android.blakkat.R;
import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Record.MediaRecord;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.User;

import static android.content.Context.MODE_PRIVATE;

public class CardAdvancementAdapter extends RecyclerView.Adapter<CardAdvancementAdapter.CardHolderSerie> {
    private ArrayList<String> _displaySeasons;
    private Media _media;
    private Context _context;
    private User _user;
    private SharedPreferences prf;

    static class CardHolderSerie extends RecyclerView.ViewHolder {
        TextView textView;
        Button seenButton;
        View v;

        CardHolderSerie(View vi) {
            super(vi);
            this.v = vi;
            this.textView = vi.findViewById(R.id.title_avancement);
            this.seenButton = vi.findViewById(R.id.check_display);
        }
    }

    public CardAdvancementAdapter(ArrayList<String> displaySeasons, Context mContext, Media media) {
        _displaySeasons = displaySeasons;
        _media = media;
        _context = mContext;
        prf = _context.getSharedPreferences("User", MODE_PRIVATE);
        _user = User.find(User.class, "pseudo = ?",
                prf.getString("User", null)).get(0);
    }

    @NonNull
    @Override
    public CardAdvancementAdapter.CardHolderSerie onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_episode_card, parent, false);
        return new CardAdvancementAdapter.CardHolderSerie(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CardAdvancementAdapter.CardHolderSerie holder, int position) {
        //info Episodes / saisons
        ArrayList<String> infoAvancement = getInfosSaisonEpisode(position);
        //media record :
        if(MediaRecord.exists(_media.getId(), _media.getProviderHint()) != null) {
            this.changeToggleViewedButtonContents(ProgressionRecord.exists(_user,infoAvancement.get(0), infoAvancement.get(1),
                    MediaRecord.exists(_media.getId(), _media.getProviderHint())) != null, holder);
        } else {
            this.changeToggleViewedButtonContents(false, holder);
        }
        holder.textView.setText(_displaySeasons.get(position));

        holder.seenButton.setOnClickListener(view -> {
            //we create a media record :
            MediaRecord record = MediaRecord.exists(_media.getId(), _media.getProviderHint());
            if(record == null) {
                record = new MediaRecord(_media);
                record.save();
            }

           if(holder.seenButton.getText().equals("Marked as viewed")) {
                switch(this._media.getProviderHint()) {
                    case "Manga":
                    case "Anime":
                        ProgressionRecord pa = new ProgressionRecord("0" ,_displaySeasons.get(position)
                                .replace("Volume : ",""));
                        pa.setMediaRecord(record);
                        pa.setUser(_user);
                        pa.save();
                        break;
                    case "Show":
                        ProgressionRecord ps = new ProgressionRecord(String.valueOf(_displaySeasons.get(position)
                                .charAt(7)) ,String.valueOf(_displaySeasons.get(position)
                                .charAt(17)));
                        ps.setMediaRecord(record);
                        ps.setUser(_user);
                        ps.save();
                        break;
                }
               changeToggleViewedButtonContents(true, holder);
           } else {
               switch(this._media.getProviderHint()) {
                   case "Manga":
                   case "Anime":
                       ProgressionRecord pa = ProgressionRecord.exists(_user, "0" ,_displaySeasons.get(position)
                               .replace("Volume : ",""), record);

                       pa.delete();
                       break;
                   case "Show":
                       ProgressionRecord ps = ProgressionRecord.exists(_user, String.valueOf(_displaySeasons.get(position)
                               .charAt(7)) ,String.valueOf(_displaySeasons.get(position)
                               .charAt(17)), record);
                       ps.delete();
                       break;
               }
               changeToggleViewedButtonContents(false, holder);
           }
        });



    }

    void changeToggleViewedButtonContents(Boolean seen, CardAdvancementAdapter.CardHolderSerie holder) {
        holder.seenButton.setText(seen == false ? R.string.notviewed : R.string.viewed);
    }
    @Override
    public int getItemCount() {
        return _displaySeasons.size();
    }

    private ArrayList<String> getInfosSaisonEpisode(int position) {
        ArrayList<String> a = new ArrayList<>();
        switch(this._media.getProviderHint()) {
            case "Manga":
            case "Anime":
                a.add("0");
                a.add(_displaySeasons.get(position)
                        .replace("Volume : ",""));
                break;
            case "Show":
                a.add(String.valueOf(_displaySeasons.get(position)
                        .charAt(7)));
                a.add(String.valueOf(_displaySeasons.get(position)
                        .charAt(17)));
                break;
        }
        return a;
    }
}
