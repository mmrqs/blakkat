package fr.efrei.android.blakkat.model;

import java.util.Date;
import java.util.List;

public class Movie implements IMedia {
    private int id;
    private String title;
    private String poster;
    private Date release_date;
    private String synopsis;
    private float score;

    public Movie() {
    }

    @Override
    public int getId() {
        return 0;
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
        return poster;
    }

    @Override
    public Date getReleaseDate() {
        return release_date;
    }

    @Override
    public float getPublicScore() {
        return score;
    }

    @Override
    public List<String> getGenres() {
        return null;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }
}
