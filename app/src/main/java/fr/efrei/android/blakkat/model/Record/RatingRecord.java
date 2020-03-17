package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.List;

public class RatingRecord extends SugarRecord {
    private int rating;
    private UserRecord userRecord;
    private MediaRecord mediaRecord;

    /**
     * Classes extending {@link SugarRecord} must have an empty constructor
     */
    public RatingRecord() {}

    /**
     * Creates a Rating record from a rating, an UserRecord and a MediaRecord
     * @param rating : rate given by the client
     * @param userRecord
     * @param mediaRecord
     */
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

    /**
     *  Check the existence of a {@link RatingRecord} with the specified UserRecord and MediaRecord in the database
     * @param userRecord
     * @param mediaRecord
     * @return the matching {@link RatingRecord}
     */
    public static RatingRecord exists(UserRecord userRecord, MediaRecord mediaRecord) {
        List<RatingRecord> res = MediaRecord.find(RatingRecord.class, "user_record = ? and media_record = ?",
                userRecord.getId().toString(), mediaRecord.getId().toString());
        return res.size() == 1 ? res.get(0) : null;
    }
}