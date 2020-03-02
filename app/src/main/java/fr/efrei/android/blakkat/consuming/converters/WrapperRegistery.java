package fr.efrei.android.blakkat.consuming.converters;

import java.util.HashMap;

import fr.efrei.android.blakkat.model.Media;
import fr.efrei.android.blakkat.model.Movie;
import fr.efrei.android.blakkat.model.Show;
import fr.efrei.android.blakkat.consuming.wrappers.IMediaWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.MovieWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.ShowWrapper;

/**
 * Only serves as a singleton dictionnary of the model classes and their corresponding wrappers
 * For instance, it will contain { Foo, FooWrapper }
 */
class WrapperRegistery {
    private static HashMap<Class<? extends Media>, Class<? extends IMediaWrapper>> wrappers;

    static Class<? extends IMediaWrapper> getWrapper(Class<? extends Media> klazz) {
        if(wrappers == null) {
            wrappers = new HashMap<>();
            wrappers.put(Show.class, ShowWrapper.class);
            wrappers.put(Movie.class, MovieWrapper.class);
        }
        return wrappers.get(klazz);
    }
}
