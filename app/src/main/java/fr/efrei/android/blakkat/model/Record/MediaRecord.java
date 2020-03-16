package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.util.List;

import fr.efrei.android.blakkat.consuming.providers.KeeperFactory;
import fr.efrei.android.blakkat.model.Media;
import retrofit2.Call;

public class MediaRecord extends SugarRecord {
    private int identifier;
    private String type;
    private String title;
    private String url;

    /**
     * Classes extending {@link SugarRecord} must have an empty constructor
     */
    public MediaRecord() { }

    /**
     * Creates a {@link MediaRecord} from a {@link Media}
     * It doesn’t check the existence of the record in the database !
     */
    public MediaRecord(Media m) {
        this.identifier = m.getId();
        this.type = m.getProviderHint();
        this.title = m.getTitle();
        this.url = m.getImageUrl();
    }

    /**
     * @return
     * this is not the physical identifier in the database, but rather the
     * matching {@link Media} internal id in its API
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * @return the type of the {@link MediaRecord} ;
     * it matches the type of the corresponding {@link Media}
     */
    public String getType() {
        return type;
    }

    /**
     * Check the existence of a {@link MediaRecord} with the specified identifier and type
     * in the database
     * @param identifier id of the {@link MediaRecord} ; also the {@link Media} id.
     * @param type of the {@link MediaRecord} ; also the type of {@link Media}
     * @return the matching {@link MediaRecord}
     */
    public static MediaRecord exists(int identifier, String type) {
        List<MediaRecord> res = MediaRecord.find(MediaRecord.class,
                "identifier = ? and type = ?",
                String.valueOf(identifier), type);
        return res.size() == 0 ? null : res.get(0);
    }

    /**
     * Obtains the {@link Media} matching the subject {@link MediaRecord}
     * @param <T> class of the corresponding {@link Media}
     * @return the matching {@link Media} if found, null otherwise
     */
    public <T extends Media> Call<T> getCorresponding() {
        return KeeperFactory.getKeeper()
                .getProviderFor(this.type)
                .getOne(this.identifier);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
