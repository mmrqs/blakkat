package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public class ProgressionRecord extends SugarRecord {
    private UserRecord userRecord;
    private MediaRecord mediaRecord;
    private Integer progressLevel1;
    private Integer progressLevel2;
    private Date made;

    public ProgressionRecord() {}

    public ProgressionRecord(Integer progressLevel1, Integer progressLevel2) {
        this.progressLevel1 = progressLevel1;
        this.progressLevel2 = progressLevel2;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public MediaRecord getMediaRecord() {
        return mediaRecord;
    }

    public Integer getProgressLevel1() {
        return progressLevel1;
    }

    public Integer getProgressLevel2() {
        return progressLevel2;
    }

    public Date getMade() {
        return made;
    }

    public ProgressionRecord markViewed(UserRecord u, MediaRecord m) {
        this.userRecord = u;
        this.mediaRecord = m;
        this.made = new Date();
        return this;
    }

    public boolean isViewed() {
        return this.made == null;
    }

    @Override
    public boolean delete() {
        this.made = null;
        return super.delete();
    }

    public static ProgressionRecord exists(UserRecord u, MediaRecord mr, Integer progressLevel1, Integer progressLevel2) {
        List<ProgressionRecord> res = ProgressionRecord.find(ProgressionRecord.class,
                "user_record = ? and progress_level1 = ? and progress_level2 = ? and media_record = ?",
                String.valueOf(u.getId()),
                String.valueOf(progressLevel1),
                String.valueOf(progressLevel2),
                String.valueOf(mr.getId()));
        return res.size() == 0 ? null : res.get(0);
    }

    public static ProgressionRecord exists(UserRecord ur, MediaRecord mr, ProgressionRecord p) {
        return exists(ur, mr, p.getProgressLevel1(), p.getProgressLevel2());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ProgressionRecord)) return false;
        ProgressionRecord c = (ProgressionRecord) o;
        return this.progressLevel2 == c.progressLevel2
                && progressLevel1 == c.progressLevel1
                && userRecord == c.userRecord
                && mediaRecord == c.mediaRecord;
    }
}
