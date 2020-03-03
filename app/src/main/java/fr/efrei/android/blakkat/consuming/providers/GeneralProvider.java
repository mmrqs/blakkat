package fr.efrei.android.blakkat.consuming.providers;

import retrofit2.Retrofit;

/**
 * Holds instances to the providers for every {@link fr.efrei.android.blakkat.model.IMedia}
 * TODO may be used in general purpose requests
 */
public class GeneralProvider {
    private IAnimeProvider animeProvider;
    private IMangaProvider mangaProvider;
    private IMovieProvider movieProvider;
    private IShowProvider showProvider;

    public GeneralProvider(Retrofit betaSeries, Retrofit jikan) {
        this.animeProvider = jikan.create(IAnimeProvider.class);
        this.mangaProvider = jikan.create(IMangaProvider.class);
        this.movieProvider = betaSeries.create(IMovieProvider.class);
        this.showProvider = betaSeries.create(IShowProvider.class);
    }

    public IAnimeProvider getAnimeProvider() {
        return animeProvider;
    }

    public IMangaProvider getMangaProvider() {
        return mangaProvider;
    }

    public IMovieProvider getMovieProvider() {
        return movieProvider;
    }

    public IShowProvider getShowProvider() {
        return showProvider;
    }
}
