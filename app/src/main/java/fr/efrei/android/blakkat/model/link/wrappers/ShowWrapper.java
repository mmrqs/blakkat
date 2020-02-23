package fr.efrei.android.blakkat.model.link.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.link.medias.Media;
import fr.efrei.android.blakkat.model.link.medias.Show;

public class ShowWrapper implements IMediaWrapper {
    private Show show;
    private List<Show> shows;

    public ShowWrapper() {}

    public void setShow(Show show) {
        this.show = show;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public Media getMedia() {
        return show;
    }

    @Override
    public List getMedias() {
        return shows;
    }
}
