package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.Manga;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMangaProvider {
    @GET("search/manga")
    Call<List<Manga>> searchFor(@Query("q") String name);
}
