package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.List;

public class ProgressionRecord extends SugarRecord {

    User user;
    MediaRecord mediaRecord;
    String av1;
    String av2;

    public ProgressionRecord() {}

    public ProgressionRecord( String av1, String av2) {
        //this.user = u;
        this.av1 = av1;
        this.av2 = av2;
        //this.mediaRecord = m;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MediaRecord getMediaRecord() {
        return mediaRecord;
    }

    public void setMediaRecord(MediaRecord mediaRecord) {
        this.mediaRecord = mediaRecord;
    }

    public String getAv1() {
        return av1;
    }

    public void setAv1(String av1) {
        this.av1 = av1;
    }

    public String getAv2() {
        return av2;
    }

    public void setAv2(String av2) {
        this.av2 = av2;
    }

    public static ProgressionRecord exists(User u, String av1, String av2, MediaRecord m) {
        List<ProgressionRecord> res = ProgressionRecord.find(ProgressionRecord.class,
                "user = ? and av1 = ? and av2 = ? and media_record = ?",
                Long.toString(u.getId()), av1, av2, Long.toString(m.getId()));
        return res.size() == 0 ? null : res.get(0);
    }
}
