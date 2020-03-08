package fr.efrei.android.blakkat.consuming.providers;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.android.blakkat.model.Media;
import retrofit2.Retrofit;

/**
 * Holds instances to the providers for every {@link fr.efrei.android.blakkat.model.Media}
 * TODO may be used in general purpose requests
 */
public class Keeper {
    private IAnimeProvider animeProvider;
    private IMangaProvider mangaProvider;
    private IMovieProvider movieProvider;
    private IShowProvider showProvider;

    private List<IProvider> providers;

    public List<IProvider> getProviders() {
        return providers;
    }

    public IProvider getProviderFor(String s) {
        switch (s) {
            case "Anime":
                return this.animeProvider;
            case "Manga":
                return this.mangaProvider;
            case "Movie":
                return this.movieProvider;
            case "Show":
                return this.showProvider;
            default:
                return null;
        }
    }

    public <T extends Media> IProvider getProviderFor(Media m) {
        return this.getProviderFor(m.getClass().getSimpleName());
    }

    Keeper(Retrofit betaSeries, Retrofit jikan) {
        this.animeProvider = jikan.create(IAnimeProvider.class);
        this.mangaProvider = jikan.create(IMangaProvider.class);
        this.movieProvider = betaSeries.create(IMovieProvider.class);
        this.showProvider = betaSeries.create(IShowProvider.class);

        initProviderList();
    }

    private void initProviderList() {
        this.providers = new ArrayList<>();
        providers.add(animeProvider);
        providers.add(movieProvider);
        providers.add(mangaProvider);
        providers.add(showProvider);
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
