package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Media;
import retrofit2.Call;

public class MediaRecord extends SugarRecord {
    private int identifier;
    private String type;
    private Date watched;
    private String title;
    private String url;

    public MediaRecord() { }

    public MediaRecord(Media m) {
        this.identifier = m.getId();
        this.type = m.getProviderHint();
        this.watched = new Date();
        this.title = m.getTitle();
        this.url = m.getImageUrl();
    }

    public Date getWatched() {
        return watched;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getIdentifierString() {
        return String.valueOf(identifier);
    }

    public String getType() {
        return type;
    }

    public static MediaRecord exists(int identifier, String type) {
        List<MediaRecord> res = MediaRecord.find(MediaRecord.class,
                "identifier = ? and type = ?",
                String.valueOf(identifier), type);
        return res.size() == 0 ? null : res.get(0);
    }

    public <T extends Media> Call<T> getCorresponding() {
        return KeeperFactory.getKeeper()
                .getProviderFor(this.type)
                .getOne(this.identifier);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
