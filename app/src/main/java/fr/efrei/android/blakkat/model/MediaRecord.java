package fr.efrei.android.blakkat.model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public class MediaRecord extends SugarRecord {
    private int identifier;
    private String type;
    private Date watched;

    /**
     * AN EMPTY CONSTRUCTOR IS MANDATORY
     */
    public MediaRecord() {
    }

    public MediaRecord(Media m) {
        this.identifier = m.getId();
        this.type = m.getProviderHint();
        this.watched = new Date();
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
}
