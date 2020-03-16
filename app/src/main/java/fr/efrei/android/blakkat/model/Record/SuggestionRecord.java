package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.List;

public class SuggestionRecord extends SugarRecord {

    private UserRecord userRecord;
    private ProgressionRecord progressionRecord;
    private MediaRecord mediaRecord;

    public SuggestionRecord() {}

    public MediaRecord getMediaRecord() {
        return mediaRecord;
    }

    public void setMediaRecord(MediaRecord mediaRecord) {
        this.mediaRecord = mediaRecord;
    }

    public UserRecord getUserRecord() {
        return userRecord;
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

    public static SuggestionRecord exists(UserRecord u, ProgressionRecord pr, MediaRecord mr) {
        List<SuggestionRecord> res = SuggestionRecord.find(SuggestionRecord.class,
                "user_record = ? and progression_record = ? and media_record = ?",
                String.valueOf(u.getId()),
                String.valueOf(pr.getId()),
                String.valueOf(mr.getId()));
        return res.size() == 0 ? null : res.get(0);
    }
}
