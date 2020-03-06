package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Show extends BetaSeriesModel<Show> {
    private int creation;
    private JSONObject genres;

    @Override
    public Date getReleaseDate() {
        if(releaseDate == null) {
            Calendar c = Calendar.getInstance();
            c.set(creation, 0, 1);
            releaseDate = c.getTime();
        }
        return releaseDate;
    }

    @Override
    public String getSynopsis() {
        return description;
    }

    @Override
    public List<String> getGenres() {
        if(this.centralizedGenres == null) {
            this.centralizedGenres = new ArrayList<>();
            for (Iterator<String> it = genres.keys(); it.hasNext(); ) {
                String s = it.next();
                try {
                    centralizedGenres.add(genres.getString(s));
                } catch (JSONException e) {
                    Log.e("BETASERIESCYKABLYAT", e.getLocalizedMessage());
                }
            }
        }
        return centralizedGenres;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        public Show[] newArray(int size) {
            return new Show[size];
        }
    };

    Show(Parcel in) {
        super(in);
        //in.readStringList(genres);
    }
}
