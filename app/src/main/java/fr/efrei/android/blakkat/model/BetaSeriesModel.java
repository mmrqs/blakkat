package fr.efrei.android.blakkat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public abstract class BetaSeriesModel<T extends IMedia> implements IMedia, Parcelable {
    int id;
    String title;
    ScoreBundle notes;
    ImageBundle images;
    List<String> genres;
    public static final Parcelable.Creator CREATOR = null;

    protected BetaSeriesModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.notes = in.readParcelable(ScoreBundle.class.getClassLoader());
        this.images = in.readParcelable(ImageBundle.class.getClassLoader());
    }

    @Override
    public int getId() {
        return id;
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
        return images.show;
    }

    @Override
    public float getPublicScore() {
        return notes.mean;
    }

    @Override
    public List<String> getGenres() {
        return genres;
    }

    static final class ScoreBundle implements Parcelable{
        private float mean;

        protected ScoreBundle(Parcel in) {
            mean = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeFloat(mean);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ScoreBundle> CREATOR = new Creator<ScoreBundle>() {
            @Override
            public ScoreBundle createFromParcel(Parcel in) {
                return new ScoreBundle(in);
            }

            @Override
            public ScoreBundle[] newArray(int size) {
                return new ScoreBundle[size];
            }
        };
    }

    static final class ImageBundle implements Parcelable{
        private String show;

        protected ImageBundle(Parcel in) {
            show = in.readString();
        }

        public static final Creator<ImageBundle> CREATOR = new Creator<ImageBundle>() {
            @Override
            public ImageBundle createFromParcel(Parcel in) {
                return new ImageBundle(in);
            }

            @Override
            public ImageBundle[] newArray(int size) {
                return new ImageBundle[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(show);
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeParcelable(this.notes, flags);
        parcel.writeParcelable(this.images, flags);
        //parcel.writeStringList(this.genres);
    }
}
