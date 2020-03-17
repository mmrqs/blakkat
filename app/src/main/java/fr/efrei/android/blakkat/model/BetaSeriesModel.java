package fr.efrei.android.blakkat.model;

import android.os.Parcel;

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
    String imageUrl;
    private Float score;
    int seasons;

    ArrayList<String> centralizedGenres;
    Date releaseDate;
    String description;

    private ScoreBundle notes;

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * {@inheritDoc}
     * We divide the score by 5 to bring it back to 1
     * @return
     */
    @Override
    public float getPublicScore() {
        if(this.score == null)
            this.score = this.notes.mean;
        return score / 5;
    }

    private static final class ScoreBundle {
        private float mean;
    }

    BetaSeriesModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.releaseDate = new Date(in.readLong());
        this.score = in.readFloat();
        this.description = in.readString();
        this.centralizedGenres = in.readArrayList(ArrayList.class.getClassLoader());
    }
}
