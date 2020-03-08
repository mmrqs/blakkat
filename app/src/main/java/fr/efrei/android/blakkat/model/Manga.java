package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manga extends JikanModel<Manga> {
    private DateBundle published;

    private Manga(Parcel in) {
        super(in);
    }

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                published.from : start_date;
    }

    @Override
    public ArrayList<?> getSeasons() {
        return null;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };
}
