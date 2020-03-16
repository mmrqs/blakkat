package fr.efrei.android.blakkat.consuming.providers;

import fr.efrei.android.blakkat.consuming.converters.WrapperConverter;
import fr.efrei.android.blakkat.model.Record.UserRecord;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class helps to generate and manage the {@link Keeper} instance
 */
public class KeeperFactory {
    private static final String BETA_SERIES_KEY = "b0781d3bbc01";
    private static final String BETA_SERIES_URL = "https://api.betaseries.com/";
    private static final String JIKAN_URL = "https://api.jikan.moe/v3/";
    private static final String JIKAN_FILTER = "12,9,44"; //ecchi, hentai, dojinshi

    private static boolean eighteen;
    private static Keeper instance;

    private static Keeper createKeeper() {
        Retrofit jikan = eighteen ? buildJikanEighteen() : buildJikan();
        Retrofit betaSeries = buildBetaSeries();

        return new Keeper(betaSeries, jikan);
    }

    public static void updateJikanSetting(boolean eighteen) {
        KeeperFactory.eighteen = eighteen;
        KeeperFactory.instance = createKeeper();
    }

    public static boolean getJikanSetting() { return eighteen; }

    /**
     * @return the {@link Keeper} instance ; creates it if necessary
     */
    public static Keeper getKeeper() {
        if (instance == null)
            instance = createKeeper();
        return instance;
    }

    public static void configureFactory(boolean isEighteen) {
        eighteen = isEighteen;
    }

    /**
     * Building the {@link Retrofit} instance for Jikan
     * @return new {@link Retrofit} instance for the Jikan API
     */
    private static Retrofit buildJikan() {
        return new Retrofit.Builder()
                .baseUrl(JIKAN_URL)
                .client(getJikanClient())
                .addConverterFactory(new WrapperConverter())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Building the {@link Retrofit} instance for Jikan
     * @return new {@link Retrofit} instance for the Jikan API
     */
    private static Retrofit buildJikanEighteen() {
        return new Retrofit.Builder()
                .baseUrl(JIKAN_URL)
                .addConverterFactory(new WrapperConverter())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Building the {@link Retrofit} instance for BetaSeries
     * Key management is handled from this point, no further worries needed
     * @return new {@link Retrofit} instance for the BetaSeries API
     */
    private static Retrofit buildBetaSeries() {
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
    private static OkHttpClient getBetaSeriesClient() {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl newUrl = original.url().newBuilder()
                    .addEncodedQueryParameter("key", BETA_SERIES_KEY)
                    .build();
            return chain.proceed(original.newBuilder()
                    .url(newUrl).build());
        }).build();
    }

    private static OkHttpClient getJikanClient() {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl newUrl = original.url().newBuilder()
                    .addEncodedQueryParameter("genre", JIKAN_FILTER)
                    .addEncodedQueryParameter("genre_exclude", "0")
                    .build();
            return chain.proceed(original.newBuilder()
                    .url(newUrl).build());
        }).build();
    }
}
