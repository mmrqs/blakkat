package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.List;

public class RatingRecord extends SugarRecord {
    private int rating;
    private UserRecord userRecord;
    private MediaRecord mediaRecord;

    public RatingRecord() {
    }

    public RatingRecord(int rating, UserRecord userRecord, MediaRecord mediaRecord) {
        this.rating = rating;
        this.userRecord = userRecord;
        this.mediaRecord = mediaRecord;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public MediaRecord getMediaRecord() {
        return mediaRecord;
    }

    public static RatingRecord exists(UserRecord userRecord, MediaRecord mediaRecord) {
        List<RatingRecord> res = MediaRecord.find(RatingRecord.class, "user_record = ? and media_record = ?",
                userRecord.getId().toString(), mediaRecord.getId().toString());
        return res.size() == 1 ? res.get(0) : null;
    }
}
