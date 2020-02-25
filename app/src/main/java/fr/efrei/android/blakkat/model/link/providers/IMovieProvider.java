package fr.efrei.android.blakkat.model.link.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.link.medias.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMovieProvider {
    String KEY = "b0781d3bbc01";

    @GET("movies/movie?key=" + KEY)
    Call<Movie> getOne(@Query("id")int id);

    @GET("movies/list?key=" + KEY)
    Call<List<Movie>> getList();
}
