package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.Anime;
import fr.efrei.android.blakkat.model.Manga;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMangaProvider extends IProvider {
    @GET("manga/{id}")
    Call<Manga> getOne(@Path("id")int id);

    @GET("search/manga")
    Call<List<Manga>> searchFor(@Query("q") String name);

    @GET("search/manga")
    Call<List<Manga>> searchForNbResults(@Query("q") String name, @Query("limit") int limit);

    @GET("search/manga?order_by=score&sort=desc")
    Call<List<Manga>> searchForNbResultsByScore(@Query("q") String name, @Query("limit") int limit);
}
