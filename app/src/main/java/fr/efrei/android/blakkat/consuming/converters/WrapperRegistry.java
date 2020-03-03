package fr.efrei.android.blakkat.consuming.converters;

import java.util.HashMap;

import fr.efrei.android.blakkat.consuming.wrappers.AnimeWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.MangaWrapper;
import fr.efrei.android.blakkat.model.Anime;
import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.model.Manga;
import fr.efrei.android.blakkat.model.Movie;
import fr.efrei.android.blakkat.consuming.wrappers.IMediaWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.MovieWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.ShowWrapper;
import fr.efrei.android.blakkat.model.Show;

/**
 * Only serves as a singleton {@link HashMap} of the model classes extending {@link IMedia}
 * and their corresponding {@link IMediaWrapper} ; for instance, it will contain { Foo, FooWrapper }
 * It differentiates wrappers for instances (Foo alone) and lists (List<Foo>)
 * Currently for {@link java.util.List}
 * {@link Anime} to {@link AnimeWrapper}
 * {@link Manga} to {@link MangaWrapper}
 * {@link Movie} to {@link MovieWrapper}
 * {@link Show} to {@link ShowWrapper}
 * Currently for instances
 * {@link Movie} to {@link MovieWrapper}
 * {@link Show} to {@link ShowWrapper}
 */
class WrapperRegistry {
    private static HashMap<Class<? extends IMedia>, Class<? extends IMediaWrapper>> instanceWrappers;
    private static HashMap<Class<? extends IMedia>, Class<? extends IMediaWrapper>> listWrappers;

    /**
     * @return the {@link HashMap} containing the instance wrappers (for Foo alone)
     */
    private static HashMap<Class<? extends IMedia>, Class<? extends IMediaWrapper>> getInstanceWrappers() {
        if(instanceWrappers == null) {
            instanceWrappers = new HashMap<>();
            instanceWrappers.put(Movie.class, MovieWrapper.class);
            instanceWrappers.put(Show.class, ShowWrapper.class);
        }
        return instanceWrappers;
    }

    /**
     * @return the {@link HashMap} containing the list wrappers (for List<Foo> alone)
     */
    private static HashMap<Class<? extends IMedia>, Class<? extends IMediaWrapper>> getListWrappers() {
        if(listWrappers == null) {
            listWrappers = new HashMap<>();
            listWrappers.put(Anime.class, AnimeWrapper.class);
            listWrappers.put(Manga.class, MangaWrapper.class);
            listWrappers.put(Movie.class, MovieWrapper.class);
            listWrappers.put(Show.class, ShowWrapper.class);
        }
        return listWrappers;
    }

    /**
     * @param klazz {@link IMedia} class for which we want the {@link IMediaWrapper}
     * @return the corresponding {@link IMediaWrapper} for a given {@link IMedia}
     */
    static Class<? extends IMediaWrapper> getInstanceWrapper(Class<? extends IMedia> klazz) {
        return getInstanceWrappers().get(klazz);
    }

    /**
     * @param klazz {@link IMedia} class for which we want the {@link IMediaWrapper}
     * @return the corresponding {@link IMediaWrapper} for a given {@link IMedia}
     */
    static Class<? extends IMediaWrapper> getListWrapper(Class<? extends IMedia> klazz) {
        return getListWrappers().get(klazz);
    }

    static boolean hasInstanceWrapper(Class<? extends IMedia> klazz) {
        return getInstanceWrappers().containsKey(klazz);
    }

    static boolean hasListWrapper(Class<? extends IMedia> klazz) {
        return getListWrappers().containsKey(klazz);
    }
}
