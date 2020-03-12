package fr.efrei.android.blakkat.helpers;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class SessionHelper {
    private static SharedPreferences preferences;
    private static Set<String> references;
    private static Gson gson;

    public static void setupPreferences(SharedPreferences sp) {
        SessionHelper.preferences = sp;
        SessionHelper.gson = new Gson();
        SessionHelper.references = new HashSet<>();
    }

    public static void save(String name, Object o) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, gson.toJson(o));
        references.add(name);
        editor.apply();
    }

    public static <T> T get(String name, Class<T> klazz) {
        return gson.fromJson(preferences.getString(name, null), klazz);
    }

    public static void remove(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(name);
        references.remove(name);
        editor.apply();
    }

    public static Set<String> getReferences() {
        return new HashSet<>(references);
    }
}
