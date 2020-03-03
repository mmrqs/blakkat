package fr.efrei.android.blakkat.model;

import java.util.Date;

public class Manga extends JikanModel<Manga> {
    private Date start_date;
    private DateBundle published;

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                published.from : start_date;
    }
}
