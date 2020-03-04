package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Show extends BetaSeriesModel<Show> {
    private String description;
    private int creation;
    private Date releaseDate;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };

    protected Show(Parcel in) {
        super(in);
        this.description = in.readString();
        this.creation = in.readInt();
        this.releaseDate = in.readParcelable(Date.class.getClassLoader());
        //in.readStringList(genres);
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeString(this.description);
        parcel.writeInt(this.creation);
        parcel.writeLong(getReleaseDate().getTime());
    }
}
