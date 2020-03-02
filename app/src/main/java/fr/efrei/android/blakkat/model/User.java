package fr.efrei.android.blakkat.model;

import com.orm.SugarRecord;

import java.security.InvalidParameterException;
import java.util.List;

public class User extends SugarRecord<User> {

    private String pseudo;

    public User() {
    }

    public User(String pseudo) {
        this.pseudo = pseudo;
    }

    public String get_pseudo() {
        return pseudo;
    }

    public static boolean getMatching(String query, String... params) {
        if(checkRequest(query, params)) {
            return User.find(User.class, "pseudo = ?", params).size() == 1;
        } else {
            throw new InvalidParameterException();
        }
    }

    private static boolean checkRequest(String query, String[] params) {
        return params.length == query.chars().filter(c -> c == '?').count();
    }

    public static boolean exists(String pseudo) {
        return getMatching("pseudo = ?", pseudo);
    }
}
