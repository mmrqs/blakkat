package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.IMedia;
import retrofit2.Call;

public interface IProvider {
    <T extends IMedia> Call<List<T>> searchFor(String title);
    <T extends IMedia> Call<List<T>> searchForNbResults(String title, int nbpp);
    <T extends IMedia> Call<T> getOne(int id);

}
