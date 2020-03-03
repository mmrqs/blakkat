package fr.efrei.android.blakkat.model;

import java.util.List;

public abstract class BetaSeries<T extends IMedia> implements IMedia {
    private int id;
    private String title;
    private ScoreBundle notes;
    private ImageBundle images;
    private List<String> genres;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getProviderHint() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImageUrl() {
        return images.show;
    }

    @Override
    public float getPublicScore() {
        return notes.mean;
    }

    @Override
    public List<String> getGenres() {
        return genres;
    }

    private static class ScoreBundle {
        private float mean;
    }

    private static class ImageBundle {
        private String show;
    }
}
