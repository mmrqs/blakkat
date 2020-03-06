package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Anime extends JikanModel<Anime> {
    private DateBundle aired;

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                aired.from : start_date;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        this.start_date = getReleaseDate();
        parcel.writeLong(start_date.getTime());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    private Anime(Parcel in) {
        super(in);
    }
}
