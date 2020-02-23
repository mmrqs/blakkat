package fr.efrei.android.blakkat.model.provider;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IShowProvider {
    String KEY = "b0781d3bbc01";

    @GET("shows/display?key=" + KEY)
    Call<Show> getOne(@Query("id")int id);
}
