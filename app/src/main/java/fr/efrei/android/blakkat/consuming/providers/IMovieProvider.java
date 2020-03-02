package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMovieProvider extends IProvider {
    @GET("movies/movie")
    Call<Movie> getOne(@Query("id")int id);

    @GET("movies/list")
    Call<List<Movie>> getList();
}
