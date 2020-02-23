package fr.efrei.android.blakkat.model.provider;

public class ShowWrapper implements IMediaWrapper {
    private Show show;

    public ShowWrapper() {}

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public Media getMedia() {
        return show;
    }
}
