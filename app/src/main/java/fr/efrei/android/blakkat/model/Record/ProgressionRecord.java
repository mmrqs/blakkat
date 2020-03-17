package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ProgressionRecord extends SugarRecord {
    private UserRecord userRecord;
    private MediaRecord mediaRecord;
    private Integer progressLevel1;
    private Integer progressLevel2;
    private Date made;

    /**
     * Classes extending {@link SugarRecord} must have an empty constructor
     */
    public ProgressionRecord() {}

    /**
     * Creates a Progression object with a season and a number
     * @param progressLevel1 : season number
     * @param progressLevel2 : episode number
     */
    public ProgressionRecord(Integer progressLevel1, Integer progressLevel2) {
        this.progressLevel1 = progressLevel1;
        this.progressLevel2 = progressLevel2;
    }

    /**
     * Creates a Progression object according to a suggestion
     * @param suggestionRecord
     */
    public ProgressionRecord(SuggestionRecord suggestionRecord) {
        this.progressLevel1 = suggestionRecord.getProgressionRecord().getProgressLevel1();
        this.progressLevel2 = suggestionRecord.getProgressionRecord().getProgressLevel2();
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public MediaRecord getMediaRecord() {
        return mediaRecord;
    }

    /**
     * Get the season number
     * @return an integer which stands for the season
     */
    public Integer getProgressLevel1() {
        return progressLevel1;
    }

    /**
     * Get the volume or episode number
     * @return integer
     */
    public Integer getProgressLevel2() {
        return progressLevel2;
    }

    /**
     * Date the user saw this media progress
     * @return Date
     */
    public Date getMade() {
        return made;
    }

    /**
     * Mark as viewed the progression for the media for a given user
     * @param u UserRecord
     * @param m MediaRecord
     * @return
     */
    public ProgressionRecord markViewed(UserRecord u, MediaRecord m) {
        this.userRecord = u;
        this.mediaRecord = m;
        this.made = new Date();
        return this;
    }

    /**
     *
     * @return a boolean : true if the progression hasn't been seen, or false otherwise
     */
    public boolean isViewed() {
        return this.made == null;
    }

    /**
     * when the progress is deleted, the date is set to null
     * @return boolean
     */
    @Override
    public boolean delete() {
        this.made = null;
        return super.delete();
    }

    /**
     * Checks if the ProgressionRecord already exists
     * @param u UserRecord
     * @param mr MediaRecord
     * @param progressLevel1 season number
     * @param progressLevel2 volume or episode number
     * @return the progressionRecord if it exist, null otherwise
     */
    public static ProgressionRecord exists(UserRecord u, MediaRecord mr, Integer progressLevel1, Integer progressLevel2) {
        List<ProgressionRecord> res = ProgressionRecord.find(ProgressionRecord.class,
                "user_record = ? and progress_level1 = ? and progress_level2 = ? and media_record = ?",
                String.valueOf(u.getId()),
                String.valueOf(progressLevel1),
                String.valueOf(progressLevel2),
                String.valueOf(mr.getId()));
        return res.size() == 0 ? null : res.get(0);
    }

    /**
     * Checks if the ProgressionRecord already exists
     * @param ur UserRecord
     * @param mr MediaRecord
     * @param p ProgressionRecord
     * @return the progressionRecord if it exist, null otherwise
     */
    public static ProgressionRecord exists(UserRecord ur, MediaRecord mr, ProgressionRecord p) {
        return exists(ur, mr, p.getProgressLevel1(), p.getProgressLevel2());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgressionRecord that = (ProgressionRecord) o;
        return Objects.equals(progressLevel1, that.progressLevel1) &&
                Objects.equals(progressLevel2, that.progressLevel2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRecord, mediaRecord, progressLevel1, progressLevel2, made);
    }
}
