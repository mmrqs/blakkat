package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Manga extends JikanModel<Manga> {
    private DateBundle published;

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

    private Manga(Parcel in) {
        super(in);
    }

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                published.from : start_date;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        this.start_date = getReleaseDate();
        parcel.writeLong(start_date.getTime());
    }
}
