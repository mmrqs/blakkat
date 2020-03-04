package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Movie extends BetaSeriesModel<Movie> {
    private String synopsis;
    private Date release_date;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    protected Movie(Parcel in) {
        super(in);
        this.synopsis = in.readString();
        this.release_date = in.readParcelable(Date.class.getClassLoader());
    }

    @Override
    public Date getReleaseDate() {
        return release_date;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeString(this.synopsis);
        parcel.writeLong(getReleaseDate().getTime());
    }
}
