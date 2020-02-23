package fr.efrei.android.blakkat.model.link.converters;

import java.util.HashMap;

import fr.efrei.android.blakkat.model.link.medias.Media;
import fr.efrei.android.blakkat.model.link.medias.Movie;
import fr.efrei.android.blakkat.model.link.medias.Show;
import fr.efrei.android.blakkat.model.link.wrappers.IMediaWrapper;
import fr.efrei.android.blakkat.model.link.wrappers.MovieWrapper;
import fr.efrei.android.blakkat.model.link.wrappers.ShowWrapper;

class WrapperRegistery {
    private static HashMap<Class<? extends Media>, Class<? extends IMediaWrapper>> wrappers;

    public static Class<? extends IMediaWrapper> getWrapper(Class<? extends Media> klazz) {
        if(wrappers == null) {
            wrappers = new HashMap<>();
            wrappers.put(Show.class, ShowWrapper.class);
            wrappers.put(Movie.class, MovieWrapper.class);
        }
        return wrappers.get(klazz);
    }
}
