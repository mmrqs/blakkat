package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.efrei.android.blakkat.model.Record.ProgressionRecord;

public class Anime extends JikanModel<Anime> {
    private DateBundle aired;
    private int episodes;

    @Override
    public Date getReleaseDate() {
        return start_date == null ?
                aired.from : start_date;
    }

    @Override
    public String getProgressLevel2Label() {
        return "Episode";
    }

    @Override
    public List<ProgressionRecord> getPossibleProgress() {
        if(records == null) {
            records = new ArrayList<>(episodes);
            for (int i = 1; i <= episodes; i++)
                records.add(new ProgressionRecord(0, i));
        }
        return records;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    private Anime(Parcel in) {
        super(in);
        this.episodes =  (Integer) in.readSerializable();
    }
}
