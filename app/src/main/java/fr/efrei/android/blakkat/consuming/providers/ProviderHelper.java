package fr.efrei.android.blakkat.consuming.providers;

import java.util.HashMap;

import fr.efrei.android.blakkat.consuming.converters.WrapperConverter;
import fr.efrei.android.blakkat.consuming.providers.exception.NoRegistredProvidedException;
import fr.efrei.android.blakkat.model.*;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProviderHelper {
    private static final String BETA_SERIES_KEY = "b0781d3bbc01";
    private static final String BETA_SERIES_URL = "http://api.betaseries.com/";
    private static final String JIKAN_URL = "http://api.betaseries.com/";

    private Retrofit jikan;
    private Retrofit betaSeries;

    private HashMap<Class<? extends IMedia>, IProvider> providerRegistry;

    public ProviderHelper() {
        this.jikan = buildJikan();
        this.betaSeries = buildBetaSeries();
        this.providerRegistry = buildProviderRegistry();
    }

    public IProvider getProviderFor(Class<? extends IMedia> mediaKlazz) {
        return providerRegistry.get(mediaKlazz);
    }

    /**
     * Build the registry to map {@link IMedia} to their {@link IProvider}
     * {@link Anime} with {@link IAnimeProvider}
     * {@link Show} with {@link IShowProvider}
     * {@link Movie} with {@link IMovieProvider}
     * @return a {@link HashMap} containing {@link IMedia} and their {@link IProvider}
     */
    private HashMap<Class<? extends IMedia>, IProvider> buildProviderRegistry() {
        HashMap<Class<? extends IMedia>, IProvider> registry = new HashMap<>();
        registry.put(Movie.class, betaSeries.create(IMovieProvider.class));
        registry.put(Show.class, betaSeries.create(IShowProvider.class));
        registry.put(Anime.class, jikan.create(IAnimeProvider.class));
        return registry;
    }

    /**
     * Building the {@link Retrofit} instance for Jikan
     * @return new {@link Retrofit} instance for the Jikan API
     */
    private Retrofit buildJikan() {
        return new Retrofit.Builder()
                .baseUrl(JIKAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Building the {@link Retrofit} instance for BetaSeries
     * Key management is handled from this point, no further worries needed
     * @return new {@link Retrofit} instance for the BetaSeries API
     */
    private Retrofit buildBetaSeries() {
        return new Retrofit.Builder()
                .baseUrl(BETA_SERIES_URL)
                .client(getBetaSeriesClient())
                .addConverterFactory(new WrapperConverter())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Building a specific {@link OkHttpClient} for BetaSeries
     * It has an interceptor that will add their key to every request made
     * @return speccifically built {@link OkHttpClient} for the BetaSeries API
     */
    private OkHttpClient getBetaSeriesClient() {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl newUrl = original.url().newBuilder()
                    .addEncodedQueryParameter("key", BETA_SERIES_KEY)
                    .build();
            return chain.proceed(original.newBuilder()
                    .url(newUrl).build());
        }).build();
    }
}
