package fr.efrei.android.blakkat.helpers;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

/**
 * Helps manage the app’s session by abstracting away the interactions with {@link SharedPreferences}
 * It also serializes and deserializes the classes with minimal interaction
 */
public class SessionHelper {
    private static SharedPreferences preferences;

    /**
     * Allows to check which values are stored into session
     */
    private static Set<String> references;
    private static Gson gson;

    /**
     * Setups the {@link SessionHelper} class by creating everything it needs
     * @param sp {@link SharedPreferences} that will be used
     */
    public static void setupPreferences(SharedPreferences sp) {
        SessionHelper.preferences = sp;
        SessionHelper.gson = new Gson();
        SessionHelper.references = new HashSet<>();
    }

    /**
     * Saves the provided object under the given name into session
     * @param name name of the object
     * @param o object to save
     */
    public static void save(String name, Object o) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, gson.toJson(o));
        references.add(name);
        editor.apply();
    }

    /**
     * Returns an object stored into session which corresponds to the provided name
     * @param name under which the wanted object is stored in session
     * @param klazz class of the wanted object
     * @param <T> type of the wanted object (enforces type checking)
     * @return the wanted object if it exists in session
     */
    public static <T> T get(String name, Class<T> klazz) {
        return gson.fromJson(preferences.getString(name, null), klazz);
    }

    /**
     * Removes an object from session
     * @param name of the object to remove
     */
    public static void remove(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(name);
        references.remove(name);
        editor.apply();
    }

    /**
     * Gives all the existing references stored in the session
     * @return the existing references
     */
    public static Set<String> getReferences() {
        return new HashSet<>(references);
    }
}
