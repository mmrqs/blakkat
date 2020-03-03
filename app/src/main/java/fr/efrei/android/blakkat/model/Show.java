package fr.efrei.android.blakkat.model;

import java.util.Date;
import java.util.List;

public class Show implements IMedia {
    private int id;
    private String title;
    private String poster;
    private Date release_date;
    private String description;
    private float score;


    public Show() {
    }

    public Show(String title){
        this.title = title;
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
        return description;
    }
}
