package in.moinkhan.preferencespider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreferenceUtils {

    private static PreferenceUtils INSTANCE;
    private Context mContext;

    private PreferenceUtils(Context context) {
        this.mContext = context;
    }

    public static PreferenceUtils getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceUtils(context);
        }
        return INSTANCE;
    }

    // string
    public void writePreferenceValue(String prefName, String prefsKey, String prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putString(prefsKey, prefsValue)
                .commit();
    }

    private void logInfo(String log) {
        if (PreferenceSpider.getInstance(mContext).isAllowLog()) {
            Log.d("PreferenceSpider", log);
        }
    }

    public String readPreferenceValue(String prefName, String prefsKey, String defaultValue) {
        String value = getPrefs(prefName).getString(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %s, Default value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, value, defaultValue));
        return value;
    }

    // int
    public void writePreferenceValue(String prefName, String prefsKey, int prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %d, value -> %d", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putInt(prefsKey, prefsValue)
                .commit();
    }

    public int readPreferenceValue(String prefName, String prefsKey, int defaultValue) {
        int val = getPrefs(prefName).getInt(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %d, Default value -> %d", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    // float
    public void writePreferenceValue(String prefName, String prefsKey, float prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %f", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putFloat(prefsKey, prefsValue)
                .commit();
    }

    public float readPreferenceValue(String prefName, String prefsKey, float defaultValue) {
        float val = getPrefs(prefName).getFloat(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %f, Default value -> %f", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    // long
    public void writePreferenceValue(String prefName, String prefsKey, long prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putLong(prefsKey, prefsValue)
                .commit();
    }

    public long readPreferenceValue(String prefName, String prefsKey, long defaultValue) {
        long val = getPrefs(prefName).getLong(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %d, Default value -> %d", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    // Set String
    public void writePreferenceValue(String prefName, String prefsKey, Set<String> prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putStringSet(prefsKey, prefsValue)
                .commit();
    }

    public Set<String> readPreferenceValue(String prefName, String prefsKey, Set<String> defaultValue) {
        Set<String> val = getPrefs(prefName).getStringSet(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %s, Default value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    // boolean
    public void writePreferenceValue(String prefName, String prefsKey, boolean prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putBoolean(prefsKey, prefsValue)
                .commit();
    }

    public boolean readPreferenceValue(String prefName, String prefsKey, boolean defaultValue) {
        boolean val = getPrefs(prefName).getBoolean(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %s, Default value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    private SharedPreferences getPrefs(String prefName) {
        if (prefName == null) {
            return PreferenceManager.getDefaultSharedPreferences(mContext);
        } else {
            return mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        }
    }

    private SharedPreferences.Editor getPrefsEditor(String prefName) {
        if (prefName == null) {
            return PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        } else {
            return mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit();
        }
    }

}