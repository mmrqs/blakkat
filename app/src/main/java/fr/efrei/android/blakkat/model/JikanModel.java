package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public abstract class JikanModel<T extends IMedia> implements IMedia, Parcelable {
    private int mal_id;
    private String title;
    private String image_url;
    private String synopsis;
    private float score;
    protected Date start_date;
    private ArrayList<MALGenre> genres;
    private ArrayList<String> curatedGenres;

    public static final Parcelable.Creator CREATOR = null;

    JikanModel(Parcel in) {
        this.mal_id = in.readInt();
        this.title = in.readString();
        this.synopsis = in.readString();
        this.image_url = in.readString();
        this.score = in.readFloat();
        this.start_date = new Date(in.readLong());
    }

    @Override
    public int getId() {
        return mal_id;
    }

    @Override
    public String getProviderHint() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImageUrl() {
        return image_url;
    }

    @Override
    public float getPublicScore() {
        return score;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public ArrayList<String> getGenres() {
        if(curatedGenres == null)
            curatedGenres = genres.stream().map(g -> g.name)
                    .collect(Collectors.toCollection(ArrayList::new));
        return curatedGenres;
    }

    private static class MALGenre {
        private String name;
    }

    static final class DateBundle {
        Date from;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mal_id);
        parcel.writeString(this.title);
        parcel.writeString(this.synopsis);
        parcel.writeString(this.image_url);
        parcel.writeFloat(this.score);
    }
}