package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.Show;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IShowProvider extends IProvider {
    @GET("shows/display")
    Call<Show> getOne(@Query("id")int id);

    @GET("shows/list")
    Call<List<Show>> getList();
}
