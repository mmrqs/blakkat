package fr.efrei.android.blakkat.consuming.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.Movie;

public class MovieWrapper implements IMediaWrapper<Movie> {
    private Movie movie;
    private List<Movie> movies;

    @Override
    public Movie getMedia() {
        return movie;
    }

    @Override
    public List<Movie> getMedias() {
        return movies;
    }
}
