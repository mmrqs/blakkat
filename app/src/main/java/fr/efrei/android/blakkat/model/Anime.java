package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Anime extends JikanModel<Anime> {
    private Date start_date;
    private DateBundle aired;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    protected Anime(Parcel in) {
        super(in);
        this.start_date = in.readParcelable(Date.class.getClassLoader());
        this.aired = in.readParcelable(DateBundle.class.getClassLoader());
    }

    @Override
    public Date getReleaseDate() {
        return start_date == null ? aired.from : start_date;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeParcelable(this.aired, flags);
        parcel.writeLong(getReleaseDate().getTime());
    }
}
