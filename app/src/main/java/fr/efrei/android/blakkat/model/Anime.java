package fr.efrei.android.blakkat.model;

import java.util.Date;

public class Anime extends JikanModel<Anime> {
    private Date start_date;
    private DateBundle aired;

    @Override
    public Date getReleaseDate() {
        return start_date == null ? aired.from : start_date;
    }
}
