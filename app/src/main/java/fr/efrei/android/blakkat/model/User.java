package fr.efrei.android.blakkat.model;

import com.orm.SugarRecord;

import java.security.InvalidParameterException;

public class User extends SugarRecord {
    private String pseudo;

    public User(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    //TODO use this in every sugarRecord
    //TODO wtf is this shit
    /**
     * Obtains the record corresponding to the specified request and parameters
     * @param query represented by a string ; parameter placeholders are ?
     * @param params list of the params ; will throw exception if the number don't match
     * @return TODO
     */
    public static boolean getMatching(String query, String... params) {
        if(checkRequest(query, params))
            return User.find(User.class,
                    "pseudo = ?", params).size() == 1;
        else
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

    public static boolean exists(String pseudo) {
        return getMatching("pseudo = ?", pseudo);
    }
}
