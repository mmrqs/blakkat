package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public abstract class JikanModel<T extends IMedia> implements IMedia, Parcelable {
    private int mal_id;
    private String title;
    private String image_url;
    private String synopsis;
    private float score;
    private ArrayList<MALGenre> genres;
    private ArrayList<String> curatedGenres;
    public static final Parcelable.Creator CREATOR = null;

    protected JikanModel(Parcel in) {
        this.mal_id = in.readInt();
        this.title = in.readString();
        this.image_url = in.readString();
        this.synopsis = in.readString();
        this.score = in.readFloat();
        this.genres = in.readArrayList(MALGenre.class.getClassLoader());
        this.curatedGenres = in.readArrayList(String.class.getClassLoader());

    }

    @Override
    public int getId() {
        return mal_id;
    }

    //TODO is this necessary ?
    @Override
    public String getProviderHint() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImageUrl() {
        return image_url;
    }

    @Override
    public float getPublicScore() {
        return score;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }

    @Override
    public ArrayList<String> getGenres() {
        if(curatedGenres == null)
            curatedGenres = genres.stream().map(g -> g.name)
                    .collect(Collectors.toCollection(ArrayList::new));
        return curatedGenres;
    }

    private static class MALGenre implements Parcelable {
        private String name;

        protected MALGenre(Parcel in) {
            name = in.readString();
        }

        public static final Creator<MALGenre> CREATOR = new Creator<MALGenre>() {
            @Override
            public MALGenre createFromParcel(Parcel in) {
                return new MALGenre(in);
            }

            @Override
            public MALGenre[] newArray(int size) {
                return new MALGenre[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
        }
    }
    static class DateBundle implements Parcelable {
        Date from;

        protected DateBundle(Parcel in) {
        }

        public static final Creator<DateBundle> CREATOR = new Creator<DateBundle>() {
            @Override
            public DateBundle createFromParcel(Parcel in) {
                return new DateBundle(in);
            }

            @Override
            public DateBundle[] newArray(int size) {
                return new DateBundle[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mal_id);
        parcel.writeString(this.title);
        parcel.writeString(this.synopsis);
        parcel.writeFloat(this.score);
        parcel.writeList(this.genres);
        parcel.writeList(this.curatedGenres);
    }
}