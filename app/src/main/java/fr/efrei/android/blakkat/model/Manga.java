package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.efrei.android.blakkat.model.Record.ProgressionRecord;

public class Manga extends JikanModel<Manga> {
    private DateBundle published;
    private int volumes;

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                published.from : start_date;
    }

    @Override
    public String getProgressLevel2Label() {
        return "Volume";
    }

    /**
     * Get all the possible progresses for a given manga, that is to say
     * the list of all volumes published
     * @return a List of {@link ProgressionRecord}
     */
    @Override
    public List<ProgressionRecord> getPossibleProgress() {
        if(records == null) {
            records = new ArrayList<>(volumes);
            for (int i = 1; i <= volumes; i++)
                records.add(new ProgressionRecord(0, i));
        }
        return records;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    private Manga(Parcel in) {
        super(in);
        this.volumes = (Integer) in.readSerializable();
    }
}
