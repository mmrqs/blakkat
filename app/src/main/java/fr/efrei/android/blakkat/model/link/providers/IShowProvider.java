package fr.efrei.android.blakkat.model.link.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.link.medias.Show;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IShowProvider {
    String KEY = "b0781d3bbc01";

    @GET("shows/display?key=" + KEY)
    Call<Show> getOne(@Query("id")int id);

    @GET("shows/list?key=" + KEY)
    Call<List<Show>> getList();
}
