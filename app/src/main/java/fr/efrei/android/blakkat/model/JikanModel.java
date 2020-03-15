package fr.efrei.android.blakkat.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a common model for objects gotten from the Jikan API
 * When a field hasn’t a java-normalized name,
 * it is for Gson mapping with Json purposes
 * @param <T> type of the media (anime or manga in this case)
 */
public abstract class JikanModel<T extends Media> extends Media {
    private int mal_id;
    private String title;
    private String image_url;
    private String synopsis;
    private float score;
    Date start_date;
    private List<String> curatedGenres;
    private List<MALGenre> genres;

    @Override
    public int getId() {
        return mal_id;
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
    public List<String> getGenres() {
        if(curatedGenres == null)
            curatedGenres = genres.stream().map(g -> g.name)
                    .collect(Collectors.toList());
        return curatedGenres;
    }

    @Override
    public String getProgressLevel1Label() {
        return null;
    }

    private static final class MALGenre {
        private String name;
    }

    static final class DateBundle {
        Date from;
    }

    JikanModel(Parcel in) {
        this.mal_id = in.readInt();
        this.title = in.readString();
        this.image_url = in.readString();
        this.start_date = new Date(in.readLong());
        this.score = in.readFloat();
        this.synopsis = in.readString();
        this.curatedGenres = in.readArrayList(ArrayList.class.getClassLoader());
    }
}