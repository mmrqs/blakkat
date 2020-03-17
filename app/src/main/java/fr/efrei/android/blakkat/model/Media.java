package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fr.efrei.android.blakkat.model.Record.MediaRecord;
import fr.efrei.android.blakkat.model.Record.ProgressionRecord;
import fr.efrei.android.blakkat.model.Record.UserRecord;

public abstract class Media implements Parcelable {
    protected List<ProgressionRecord> records;

    /**
     * Return the Media type
     * @return a string
     */
    public String getProviderHint() {
        return this.getClass().getSimpleName();
    }

    /**
     *
     * @return an integer representing the id of the media
     */
    public abstract int getId();
    public abstract String getTitle();

    /**
     * return the media image
     * @return
     */
    public abstract String getImageUrl();

    /**
     * returns the date on which the media was published
     * @return Date
     */
    public abstract Date getReleaseDate();
    public abstract float getPublicScore();
    public abstract String getSynopsis();
    public abstract List<String> getGenres();

    /**
     *
     * @return a list of {@link ProgressionRecord} which represents all the possible
     * progressions for a given media
     */
    public abstract List<ProgressionRecord> getPossibleProgress();

    /**
     * Get the label of the first level of progression
     * @return a string representing the label of the first level of progression : season if it
     * is a show, null otherwise
     */
    public abstract String getProgressLevel1Label();

    /**
     * Get the label of the second level of progression
     * @return a string representing the label of the second level of progression : episode if it is a
     * show or an anime, volume if it is a manga.
     */
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
    }

    /**
     * Return a list of possible suggestions for a given media according to user progress
     * @param user {@link UserRecord}
     * @param mr {@link MediaRecord}
     * @return list of {@link ProgressionRecord}
     */
    public List<ProgressionRecord> getPossibleSuggestions(UserRecord user, MediaRecord mr) {
        List<ProgressionRecord> possibleProgress = this.getPossibleProgress();

        List<ProgressionRecord> progressMade = ProgressionRecord.find(ProgressionRecord.class,
                "user_record = ? and media_record = ?",
                user.getId().toString(), mr.getId().toString());

        List<ProgressionRecord> suggestions = new ArrayList<>(possibleProgress);
        suggestions.removeAll(progressMade);

        return suggestions;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
