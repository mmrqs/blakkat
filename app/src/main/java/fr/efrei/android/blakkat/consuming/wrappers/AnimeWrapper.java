package fr.efrei.android.blakkat.consuming.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.Anime;

public class AnimeWrapper implements IMediaWrapper<Anime> {
    private List<Anime> results;

    @Override
    public Anime getMedia() {
        return null;
    }

    @Override
    public List<Anime> getMedias() {
        return results;
    }
}
