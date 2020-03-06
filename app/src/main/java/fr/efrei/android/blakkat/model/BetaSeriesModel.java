package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a common model for objects gotten from the BetaSeries API
 * When a field hasn’t a java-normalized name,
 * it is for Gson mapping with Json purposes
 * @param <T> type of the media (anime or manga in this case)
 */
public abstract class BetaSeriesModel<T extends Media> extends Media {
    private int id;
    private String title;
    private String url;
    private Float score;
    Date releaseDate;
    String description;

    private ScoreBundle notes;
    private ImageBundle images;
    private ArrayList<String> genres;

    @Override
    public int getId() {
        return id;
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

    protected BetaSeriesModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.url = in.readString();
        this.releaseDate = new Date(in.readLong());
        this.score = in.readFloat();
        this.description = in.readString();
    }
}
