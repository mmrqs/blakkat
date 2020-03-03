package fr.efrei.android.blakkat.model;

import java.util.Calendar;
import java.util.Date;

public class Show extends BetaSeries<Show> {
    private String description;
    private int creation;
    private Date releaseDate;

    @Override
    public Date getReleaseDate() {
        if(releaseDate == null) {
            Calendar c = Calendar.getInstance();
            c.set(creation, 0, 1);
            releaseDate = c.getTime();
        }
        return releaseDate;
    }

    @Override
    public String getSynopsis() {
        return description;
    }
}
