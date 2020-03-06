package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Movie extends BetaSeriesModel<Movie> {
    private String synopsis;
    private Date release_date;

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
