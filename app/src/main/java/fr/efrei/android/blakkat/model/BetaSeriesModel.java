package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.dsl.Ignore;

import java.util.ArrayList;

public abstract class BetaSeriesModel<T extends IMedia> implements IMedia, Parcelable {
    int id;
    @Ignore
    String title;
    @Ignore
    String url;
    @Ignore
    Float score;
    @Ignore
    ScoreBundle notes;
    @Ignore
    ImageBundle images;
    @Ignore
    ArrayList<String> genres;

    @Override
    public int getId() {
        return id;
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
        if(this.url == null)
            url = this.images.show;
        return url;
    }

    @Override
    public float getPublicScore() {
        if(this.score == null)
            this.score = this.notes.mean;
        return score;
    }

    @Override
    public ArrayList<String> getGenres() {
        return genres;
    }

    private static final class ScoreBundle {
        private float mean;
    }

    private static final class ImageBundle {
        private String show;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeFloat(this.getPublicScore());
        parcel.writeString(this.getImageUrl());
        parcel.writeList(this.genres);
    }

    public static final Parcelable.Creator CREATOR = null;

    protected BetaSeriesModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.score = in.readFloat();
        this.url = in.readString();
        this.genres = in.readArrayList(String.class.getClassLoader());
    }
}
