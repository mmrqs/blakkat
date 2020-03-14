package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.SuggestionRecord;

public class Show extends BetaSeriesModel<Show> {
    private int creation;
    private JsonObject genres;
    private ImageBundle images;
    private List<SeasonsBundle> seasons_details;

    @Override
    public Date getReleaseDate() {
        if(releaseDate == null) {
            Calendar c = Calendar.getInstance();
            c.set(creation, 0, 1);
            releaseDate = c.getTime();
        }
        return releaseDate;
    }

    @Override
    public String getSynopsis() {
        return description;
    }

    @Override
    public List<String> getGenres() {
        if(this.centralizedGenres == null) {
            this.centralizedGenres = new ArrayList<>();
            centralizedGenres.addAll(genres.keySet());
        }
        return centralizedGenres;
    }

    @Override
    public String getProgressLevel1Label() {
        return "Season";
    }

    @Override
    public String getProgressLevel2Label() {
        return "Episode";
    }

    @Override
    public List<ProgressionRecord> getPossibleProgress() {
        if(records == null) {
            records = new ArrayList<>(seasons_details.size() *
                    seasons_details.get(0).episodes);
            seasons_details.forEach(s -> {
                for (int i = 1; i <= s.episodes; i++)
                    records.add(new ProgressionRecord(s.number, i));
            });
        }
        return records;
    }

    @Override
    public String getImageUrl() {
        if(this.imageUrl == null)
            imageUrl = this.images.show;
        return imageUrl;
    }

    private static final class ImageBundle {
        private String show;
    }

    private static final class SeasonsBundle {
        private int number;
        private int episodes;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };

    Show(Parcel in) {
        super(in);
    }

}
