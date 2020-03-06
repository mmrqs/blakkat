package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.dsl.Column;
import com.orm.dsl.Ignore;

import java.util.ArrayList;

/**
 * Represents a common model for objects gotten from the BetaSeries API
 * When a field hasn’t a java-normalized name,
 * it is for Gson mapping with Json purposes
 * @param <T> type of the media (anime or manga in this case)
 */
public abstract class BetaSeriesModel<T extends Media> extends Media {
    int id;
    
    String title;
    
    String url;
    
    Float score;
    
    ScoreBundle notes;
    
    ImageBundle images;
    
    ArrayList<String> genres;

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

    public static final Parcelable.Creator CREATOR = null;

    protected BetaSeriesModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.score = in.readFloat();
        this.url = in.readString();
        this.genres = in.readArrayList(String.class.getClassLoader());
    }
}
