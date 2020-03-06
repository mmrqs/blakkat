package fr.efrei.android.blakkat.model;

import com.orm.SugarRecord;

public class MediaRecord extends SugarRecord {
    private int identifier;
    private String type;

    public MediaRecord(Media m) {
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
