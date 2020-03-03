package fr.efrei.android.blakkat.consuming.converters;

import java.util.HashMap;

import fr.efrei.android.blakkat.consuming.wrappers.AnimeWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.MangaWrapper;
import fr.efrei.android.blakkat.model.Anime;
import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.model.Manga;
import fr.efrei.android.blakkat.model.Movie;
import fr.efrei.android.blakkat.model.Show;
import fr.efrei.android.blakkat.consuming.wrappers.IMediaWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.MovieWrapper;
import fr.efrei.android.blakkat.consuming.wrappers.ShowWrapper;

/**
 * Only serves as a singleton {@link HashMap} of the model classes extending {@link IMedia}
 * and their corresponding wrappers ; for instance, it will contain { Foo, FooWrapper }
 * Currently
 * {@link Anime} to {@link AnimeWrapper}
 * {@link Manga} to {@link MangaWrapper}
 * {@link Movie} to {@link MovieWrapper}
 * {@link Show} to {@link ShowWrapper}
 */
class WrapperRegistery {
    private static HashMap<Class<? extends IMedia>, Class<? extends IMediaWrapper>> wrappers;

    static Class<? extends IMediaWrapper> getWrapper(Class<? extends IMedia> klazz) {
        if(wrappers == null) {
            wrappers = new HashMap<>();
            wrappers.put(Anime.class, AnimeWrapper.class);
            wrappers.put(Manga.class, MangaWrapper.class);
            wrappers.put(Movie.class, MovieWrapper.class);
            wrappers.put(Show.class, ShowWrapper.class);
        }
        return wrappers.get(klazz);
    }
}
