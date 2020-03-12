package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.efrei.android.blakkat.model.Record.ProgressionRecord;

public class Movie extends BetaSeriesModel<Movie> {
    private String synopsis;
    private Date release_date;
    private ArrayList<String> genres;
    private String poster;

    @Override
    public Date getReleaseDate() {
        if(releaseDate == null)
            releaseDate = release_date;
        return releaseDate;
    }

    @Override
    public String getSynopsis() {
        if(description == null)
            description = synopsis;
        return description;
    }

    @Override
    public String getImageUrl() {
        if(this.imageUrl == null)
            this.imageUrl = poster;
        return imageUrl;
    }

    @Override
    public String getProgressLevel1Label() {
        return "Film";
    }

    @Override
    public String getProgressLevel2Label() {
        return null;
    }

    @Override
    public ArrayList<String> getGenres() {
        if(this.centralizedGenres == null)
            this.centralizedGenres = this.genres;
        return this.centralizedGenres;
    }

    @Override
    public List<ProgressionRecord> getPossibleProgress() {
        List<ProgressionRecord> records = new ArrayList<>();
        records.add(new ProgressionRecord(1, 0));
        return records;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    Movie(Parcel in) {
        super(in);
    }
}
