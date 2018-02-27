package in.co.moinkhan.preferencespider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

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


    // float
    public static void writePreferenceValue(Context context, String prefsKey, float prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putFloat(prefsKey, prefsValue);
        editor.commit();
    }

    public static float readPreferenceValue(Context context, String prefsKey, float defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(prefsKey, defaultValue);
    }


    // long
    public static void writePreferenceValue(Context context, String prefsKey, long prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putLong(prefsKey, prefsValue);
        editor.commit();
    }

    public static long readPreferenceValue(Context context, String prefsKey, long defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(prefsKey, defaultValue);
    }

    // Set String
    public static void writePreferenceValue(Context context, String prefsKey, Set<String> prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putStringSet(prefsKey, prefsValue);
        editor.commit();
    }

    public static Set<String> readPreferenceValue(Context context, String prefsKey, Set<String> defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getStringSet(prefsKey, defaultValue);
    }

    // boolean
    public static void writePreferenceValue(Context context, String prefsKey, boolean prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putBoolean(prefsKey, prefsValue);
        editor.commit();
    }

    public static boolean readPreferenceValue(Context context, String prefsKey, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(prefsKey, defaultValue);
    }

    private static SharedPreferences.Editor getPrefsEditor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.edit();
    }
    
}