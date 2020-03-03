package fr.efrei.android.blakkat.consuming.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.Manga;

public class MangaWrapper implements IMediaWrapper<Manga> {
    private List<Manga> results;

    public MangaWrapper() {
    }

    @Override
    public Manga getMedia() {
        return null;
    }

    @Override
    public List<Manga> getMedias() {
        return results;
    }
}
