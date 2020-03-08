package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Show extends BetaSeriesModel<Show> {
    private int creation;
    private JsonObject genres;
    private ImageBundle images;
    private List<SeasonsBundle> seasons_details;
    private HashMap<Integer, Integer> centralizedSeasons;

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
    public HashMap<Integer, Integer> getSeasons() {
        if(this.centralizedSeasons == null) {
            this.centralizedSeasons = new HashMap<>();
            for(SeasonsBundle s : seasons_details) {
                this.centralizedSeasons.put(s.number, s.episodes);
            }
        }
        return this.centralizedSeasons;
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
        this.centralizedSeasons = (HashMap<Integer, Integer>) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeSerializable(getSeasons());
    }

}
