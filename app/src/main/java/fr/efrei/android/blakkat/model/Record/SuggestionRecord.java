package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.List;
import java.util.Objects;

public class SuggestionRecord extends SugarRecord {
    private UserRecord userRecord;
    private ProgressionRecord progressionRecord;
    private MediaRecord mediaRecord;

    /**
     * Classes extending {@link SugarRecord} must have an empty constructor
     */
    public SuggestionRecord() {}

    /**
     * Creates a {@link SuggestionRecord} from a {@link UserRecord} and a {@link MediaRecord}
     * It doesn’t check the existence of the record in the database !
     */
    public SuggestionRecord(UserRecord userRecord, MediaRecord mediaRecord) {
        this.userRecord = userRecord;
        this.mediaRecord = mediaRecord;
    }

    public MediaRecord getMediaRecord() {
        return mediaRecord;
    }

    public void setMediaRecord(MediaRecord mediaRecord) {
        this.mediaRecord = mediaRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    public ProgressionRecord getProgressionRecord() {
        return progressionRecord;
    }

    public void setProgressionRecord(ProgressionRecord progressionRecord) {
        this.progressionRecord = progressionRecord;
    }

    /**
     * Check the existence of a {@link SuggestionRecord} with the specified {@link UserRecord} and
     * a {@link ProgressionRecord} and a {@link MediaRecord}
     * @param u {@link UserRecord}
     * @param pr {@link ProgressionRecord}
     * @param mr {@link MediaRecord}
     * @return the matching {@link SuggestionRecord}
     */
    public static SuggestionRecord exists(UserRecord u, ProgressionRecord pr, MediaRecord mr) {
        List<SuggestionRecord> res = SuggestionRecord.find(SuggestionRecord.class,
                "user_record = ? and progression_record = ? and media_record = ?",
                String.valueOf(u.getId()),
                String.valueOf(pr.getId()),
                String.valueOf(mr.getId()));
        return res.size() == 0 ? null : res.get(0);
    }
}
