package fr.efrei.android.blakkat.model.Record;

import com.orm.SugarRecord;

import java.security.InvalidParameterException;
import java.util.List;

public class UserRecord extends SugarRecord {
    private String pseudo;
    private boolean eighteen;
    private boolean enableNotifs;

    /**
     * Classes extending {@link SugarRecord} must have an empty constructor
     */
    public UserRecord() {}

    /**
     * Creates a {@link UserRecord} from a Pseudo with a string type
     * It doesn’t check the existence of the record in the database !
     */
    public UserRecord(String pseudo) {
        this.pseudo = pseudo;
        this.eighteen = false;
        this.enableNotifs = false;
    }

    public String getPseudo() {
        return pseudo;
    }

    public boolean isEighteen() {
        return eighteen;
    }

    public void setEighteen(boolean eighteen) {
        this.eighteen = eighteen;
    }

    /**
     * Obtains the record corresponding to the specified request and parameters
     * @param query represented by a string ; parameter placeholders are ?
     * @param params list of the params ; will throw exception if the number don't match
     * @return TODO
     */
    public static UserRecord getMatching(String query, String... params) {
        if(checkRequest(query, params)) {
            List<UserRecord> res = UserRecord.find(UserRecord.class, query, params);
            return res.size() == 1 ? res.get(0) : null;
        } else
            throw new InvalidParameterException();
    }

    /**
     * Checks if the number of wildcards (?) in the query is the same as
     * the number of parameters
     * @param query to validate
     * @param params given params
     * @return true if the number match
     */
    private static boolean checkRequest(String query, String[] params) {
        return params.length == query.chars()
                .filter(c -> c == '?').count();
    }

    /**
     * Check the existence of a {@link UserRecord} with the specified Pseudo
     * @param pseudo String
     * @return the matching {@link UserRecord}
     */
    public static UserRecord exists(String pseudo) {
        return getMatching("pseudo = ?", pseudo);
    }

    /**
     * Returns if user agrees to receive notifications
     * @return true if user agrees to receive notifications, false otherwise
     */
    public boolean isEnableNotifs() {
        return enableNotifs;
    }

    /**
     *
     * @param enableNotifs a boolean
     */
    public void setEnableNotifs(boolean enableNotifs) {
        this.enableNotifs = enableNotifs;
    }
}
