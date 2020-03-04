package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Manga extends JikanModel<Manga> {
    private Date start_date;
    private DateBundle published;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    protected Manga(Parcel in) {
        super(in);
        this.start_date = in.readParcelable(Date.class.getClassLoader());
        this.published = in.readParcelable(DateBundle.class.getClassLoader());
    }

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                published.from : start_date;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeParcelable(this.published, flags);
        parcel.writeLong(getReleaseDate().getTime());
    }
}
