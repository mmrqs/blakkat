package fr.efrei.android.blakkat.consuming.wrappers;

import java.util.List;

import fr.efrei.android.blakkat.model.IMedia;
import fr.efrei.android.blakkat.model.Show;

public class ShowWrapper implements IMediaWrapper {
    private Show show;
    private List<Show> shows;

    public ShowWrapper() {}

    @Override
    public Show getMedia() {
        return show;
    }

    @Override
    public List<Show> getMedias() {
        return shows;
    }
}
