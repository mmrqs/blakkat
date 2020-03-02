package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.Anime;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAnimeProvider {
    @GET("search/anime")
    Call<List<Anime>> searchFor(@Query("q")String name);
}
