package fr.efrei.android.blakkat.consuming.providers;

import java.util.List;

import fr.efrei.android.blakkat.model.IMedia;
import retrofit2.Call;

/**
 * This class serves as a common ground for every Provider interface
 * It doesn’t coerce them into implementing these declared methods,
 * it allows to generically call relevant methods in batch rather than
 * taking each interface individually.
 * Due to {@link retrofit2.Retrofit} not supporting parametrized (<T>)
 * interfaces, this cannot be parametrized and type cannot be truly enforced.
 * This, however, seems like a reasonable trade.
 */
public interface IProvider {
    <T extends IMedia> Call<List<T>> searchFor(String title);
    <T extends IMedia> Call<T> getOne(int id);
    String niktamer();
}
