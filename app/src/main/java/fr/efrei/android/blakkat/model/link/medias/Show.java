package fr.efrei.android.blakkat.model.link.medias;

public class Show implements Media {
    private int id;
    private String title;

    public Show() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
