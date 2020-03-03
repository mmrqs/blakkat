package fr.efrei.android.blakkat.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class JikanModel<T extends IMedia> implements IMedia {
    private int mal_id;
    private String title;
    private String image_url;
    private String synopsis;
    private float score;
    private List<MALGenre> genres;
    private List<String> curatedGenres;

    @Override
    public int getId() {
        return mal_id;
    }

    //TODO is this necessary ?
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
        return image_url;
    }

    @Override
    public float getPublicScore() {
        return score;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public List<String> getGenres() {
        if(curatedGenres == null)
            curatedGenres = genres.stream()
                    .map(g -> g.name)
                    .collect(Collectors.toList());
        return curatedGenres;
    }

    private static class MALGenre {
        private String name;
    }
    static class DateBundle {
        Date from;
    }
}
