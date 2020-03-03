package fr.efrei.android.blakkat.model;

import java.util.Date;
import java.util.List;

public class Manga implements IMedia {
    private int mal_id;
    private String title;
    private String image_url;
    private Date start_date;
    private String synopsis;
    private float score;

    public Manga() {}

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
    public Date getReleaseDate() {
        return start_date;
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
        return null;
    }
}
