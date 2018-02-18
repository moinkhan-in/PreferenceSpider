package in.co.moinkhan.preferencespider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    // string
    public static void writePreferenceValue(Context context, String prefsKey, String prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putString(prefsKey, prefsValue);
        editor.commit();
    }

    public static String readPreferenceValue(Context context, String prefsKey, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(prefsKey, defaultValue);
    }

    // int
    public static void writePreferenceValue(Context context, String prefsKey, int prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putInt(prefsKey, prefsValue);
        editor.commit();
    }

    public static int readPreferenceValue(Context context, String prefsKey, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(prefsKey, defaultValue);
    }

    // boolean
    public static void writePreferenceValue(Context context, String prefsKey, boolean prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putBoolean(prefsKey, prefsValue);
        editor.commit();
    }

    private static SharedPreferences.Editor getPrefsEditor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.edit();
    }
    
}