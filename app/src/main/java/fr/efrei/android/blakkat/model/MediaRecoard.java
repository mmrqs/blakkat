package fr.efrei.android.blakkat.model;

import com.orm.SugarRecord;

public class MediaRecoard extends SugarRecord {
    private int identifier;
    private String type;

    /**
     * AN EMPTY CONSTRUCTOR IS MANDATORY
     */
    public MediaRecoard() {
    }

    public MediaRecoard(Media m) {
        this.identifier = m.getId();
        this.type = m.getProviderHint();
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }
}
