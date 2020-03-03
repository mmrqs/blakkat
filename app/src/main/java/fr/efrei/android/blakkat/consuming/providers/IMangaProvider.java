package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.Anime;
import fr.efrei.android.blakkat.model.Manga;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMangaProvider {
    @GET("manga/{id}")
    Call<Manga> getOne(@Path("id")int id);

    @GET("search/manga")
    Call<List<Manga>> searchFor(@Query("q") String name);
}
