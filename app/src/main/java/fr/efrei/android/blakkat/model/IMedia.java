package fr.efrei.android.blakkat.model;

import java.util.Date;
import java.util.List;

public interface IMedia {
    int getId();
    String getProviderHint();
    String getTitle();
    String getImageUrl();
    Date getReleaseDate();
    float getPublicScore();
    String getSynopsis();
    List<String> getGenres();
}
