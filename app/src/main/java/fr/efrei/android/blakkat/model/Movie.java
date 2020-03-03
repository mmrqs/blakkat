package fr.efrei.android.blakkat.model;

import java.util.Date;

public class Movie extends BetaSeries<Movie> {
    private String synopsis;
    private Date release_date;

    @Override
    public Date getReleaseDate() {
        return release_date;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }}
