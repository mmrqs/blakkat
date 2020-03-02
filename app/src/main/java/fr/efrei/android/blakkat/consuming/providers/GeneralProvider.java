package fr.efrei.android.blakkat.consuming.providers;

import retrofit2.Retrofit;

public class GeneralProvider {
    private IAnimeProvider animeProvider;
    private IMovieProvider movieProvider;
    private IShowProvider showProvider;

    public GeneralProvider(Retrofit betaSeries, Retrofit jikan) {
        this.animeProvider = jikan.create(IAnimeProvider.class);
        this.movieProvider = betaSeries.create(IMovieProvider.class);
        this.showProvider = betaSeries.create(IShowProvider.class);
    }

    public IAnimeProvider getAnimeProvider() {
        return animeProvider;
    }

    public IMovieProvider getMovieProvider() {
        return movieProvider;
    }

    public IShowProvider getShowProvider() {
        return showProvider;
    }
}
