package fr.efrei.android.blakkat.helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * {@link Toast} helper class which only shows {@link Toast}s.
 */
public class Toaster {
    /**
     * Short duration {@link Toast}
     * @param c context for the {@link Toast}
     * @param t text of the {@link Toast}
     */
    public static void toast(Context c, String t) {
        Toaster.toast(c, t, Toast.LENGTH_SHORT);
    }

    /**
     * Long duration {@link Toast}
     * @param c context for the {@link Toast}
     * @param t text of the {@link Toast}
     */
    public static void burn(Context c, String t) {
        Toaster.toast(c, t, Toast.LENGTH_LONG);
    }

    /**
     * Master {@link Toast}
     * @param c context for the {@link Toast}
     * @param t text of the {@link Toast}
     * @param l duration (length) of the {@link Toast}
     */
    private static void toast(Context c, String t, int l) {
        Toast.makeText(c, t, l).show();
    }
}
