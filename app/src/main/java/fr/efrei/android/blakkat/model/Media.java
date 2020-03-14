package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fr.efrei.android.blakkat.model.Record.ProgressionRecord;

public abstract class Media implements Parcelable {
    protected List<ProgressionRecord> records;

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
    public abstract List<ProgressionRecord> getPossibleProgress();
    public abstract String getProgressLevel1Label();
    public abstract String getProgressLevel2Label();

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.getId());
        parcel.writeString(this.getTitle());
        parcel.writeString(this.getImageUrl());
        parcel.writeLong(this.getReleaseDate() == null ?
                0 : this.getReleaseDate().getTime());
        parcel.writeFloat(this.getPublicScore());
        parcel.writeString(this.getSynopsis());
        parcel.writeList(this.getGenres());
        //parcel.writeSerializable(this.getPossibleProgress());
    }

    public List<ProgressionRecord> getPossibleSuggestion() {
        List<ProgressionRecord> suggestions = this.getPossibleProgress();
        Iterator<ProgressionRecord> it = suggestions.iterator();

        while(it.hasNext()) {
            if(!it.next().isViewed()) it.remove();
        }
        return suggestions;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
