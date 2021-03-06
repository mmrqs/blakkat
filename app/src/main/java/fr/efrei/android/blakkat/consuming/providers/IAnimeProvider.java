package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.Anime;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAnimeProvider extends IProvider {
    @GET("anime/{id}")
    Call<Anime> getOne(@Path("id")int id);

    @GET("search/anime")
    Call<List<Anime>> searchFor(@Query("q")String name);

    @GET("search/anime")
    Call<List<Anime>> searchForNbResults(@Query("q") String name, @Query("limit") int limit);

    @GET("search/anime?order_by=score&sort=desc")
    Call<List<Anime>> searchForNbResultsByScore(@Query("q") String name, @Query("limit") int limit);
}
