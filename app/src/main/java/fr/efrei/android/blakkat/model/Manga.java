package fr.efrei.android.blakkat.model;

public class Manga implements IMedia {
    private int mal_id;
    private String title;

    public Manga() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return mal_id;
    }

    public void setId(int id) {
        this.mal_id = id;
    }
}
