package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Manga extends JikanModel<Manga> {
    private DateBundle published;
    private int volumes;

    private Manga(Parcel in) {
        super(in);
        this.volumes = (Integer) in.readSerializable();
    }

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                published.from : start_date;
    }

    @Override
    public Integer getSeasons() {
        return this.volumes;
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
