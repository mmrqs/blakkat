package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public abstract class Media implements Parcelable {
    public String getProviderHint() {
        return this.getClass().getSimpleName();
    }

    public abstract int getId();
    public abstract String getTitle();
    public abstract String getImageUrl();
    public abstract Date getReleaseDate();
    public abstract float getPublicScore();
    public abstract String getSynopsis();
    public abstract List<String> getGenres();

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.getId());
        parcel.writeString(this.getTitle());
        parcel.writeString(this.getImageUrl());
        parcel.writeLong(this.getReleaseDate().getTime());
        parcel.writeFloat(this.getPublicScore());
        parcel.writeString(this.getSynopsis());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
