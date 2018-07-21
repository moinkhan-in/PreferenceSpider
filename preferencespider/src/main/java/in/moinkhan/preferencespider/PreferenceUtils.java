package in.moinkhan.preferencespider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Set;

public final class PreferenceUtils {

    private static PreferenceUtils INSTANCE;
    private Context mContext;

    private PreferenceUtils(Context context) {
        this.mContext = context;
    }

    public static synchronized PreferenceUtils getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceUtils(context);
        }
        return INSTANCE;
    }

    private void logInfo(String log) {
        if (PreferenceSpider.getInstance().isAllowLog()) {
            Log.d("PreferenceSpider", log);
        }
    }

    // string
    public void writeString(String prefName, String prefsKey, String prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putString(prefsKey, prefsValue)
                .commit();
    }

    public void writeString(String prefsKey, String prefsValue) {
        writeString(null, prefsKey, prefsValue);
    }

    public String readString(String prefName, String prefsKey, String defaultValue) {
        String value = getPrefs(prefName).getString(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %s, Default value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, value, defaultValue));
        return value;
    }

    public String readString(String prefsKey, String defaultValue) {
        return readString(null, prefsKey, defaultValue);
    }

    public String readString(String prefsKey) {
        return readString(null, prefsKey, null);
    }

    // int
    public void writeInt(String prefName, String prefsKey, int prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %d", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putInt(prefsKey, prefsValue)
                .commit();
    }

    public void writeInt(String prefsKey, int prefsValue) {
        writeInt(null, prefsKey, prefsValue);
    }

    public int readInt(String prefName, String prefsKey, int defaultValue) {
        int val = getPrefs(prefName).getInt(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %d, Default value -> %d", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    public int readInt(String prefsKey, int defaultValue) {
        return readInt(null, prefsKey, defaultValue);
    }

    public int readInt(String prefsKey) {
        return readInt(null, prefsKey, 0);
    }

    // float
    public void writeFloat(String prefName, String prefsKey, float prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %f", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putFloat(prefsKey, prefsValue)
                .commit();
    }

    public void writeFloat(String prefsKey, float prefsValue) {
        writeFloat(null, prefsKey, prefsValue);
    }

    public float readFloat(String prefName, String prefsKey, float defaultValue) {
        float val = getPrefs(prefName).getFloat(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %f, Default value -> %f", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    public float readFloat(String prefsKey, float defaultValue) {
        return readFloat(null, prefsKey, defaultValue);
    }

    public float readFloat(String prefsKey) {
        return readFloat(null, prefsKey, 0f);
    }

    // long
    public void writeLong(String prefName, String prefsKey, long prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putLong(prefsKey, prefsValue)
                .commit();
    }

    public void writeLong(String prefsKey, long prefsValue) {
        writeLong(null, prefsKey, prefsValue);
    }

    public long readLong(String prefName, String prefsKey, long defaultValue) {
        long val = getPrefs(prefName).getLong(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %d, Default value -> %d", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    public long readLong(String prefsKey, long defaultValue) {
        return readLong(null, prefsKey, defaultValue);
    }

    public long readLong(String prefsKey) {
        return readLong(null, prefsKey, 0L);
    }

    // long
    public void writeDouble(String prefName, String prefsKey, double prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putLong(prefsKey, Double.doubleToRawLongBits(prefsValue))
                .commit();
    }

    public void writeDouble(String prefsKey, double prefsValue) {
        writeDouble(null, prefsKey, prefsValue);
    }

    public double readDouble(String prefName, String prefsKey, double defaultValue) {
        double val = Double.longBitsToDouble(getPrefs(prefName).getLong(prefsKey, Double.doubleToRawLongBits(defaultValue)));
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %f, Default value -> %f", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    public double readDouble(String prefsKey, double defaultValue) {
        return readDouble(null, prefsKey, defaultValue);
    }

    public double readDouble(String prefsKey) {
        return readDouble(null, prefsKey, 0);
    }

    // Set String
    public void writeStringSet(String prefName, String prefsKey, Set<String> prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putStringSet(prefsKey, prefsValue)
                .commit();
    }

    public void writeStringSet(String prefsKey, Set<String> prefsValue) {
        writeStringSet(null, prefsKey, prefsValue);
    }

    public Set<String> readStringSet(String prefName, String prefsKey, Set<String> defaultValue) {
        Set<String> val = getPrefs(prefName).getStringSet(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %s, Default value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    public Set<String> readStringSet(String prefsKey, Set<String> defaultValue) {
        return readStringSet(null, prefsKey, defaultValue);
    }

    public Set<String> readStringSet(String prefsKey) {
        return readStringSet(null, prefsKey, null);
    }

    // boolean
    public void writeBoolean(String prefName, String prefsKey, boolean prefsValue) {
        logInfo(String.format(Locale.getDefault(), ">>> Name -> %s, Key -> %s, value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, prefsValue));
        getPrefsEditor(prefName)
                .putBoolean(prefsKey, prefsValue)
                .commit();
    }

    public void writeBoolean(String prefsKey, boolean prefsValue) {
        writeBoolean(null, prefsKey, prefsValue);
    }

    public boolean readBoolean(String prefName, String prefsKey, boolean defaultValue) {
        boolean val = getPrefs(prefName).getBoolean(prefsKey, defaultValue);
        logInfo(String.format(Locale.getDefault(), "<<< Name -> %s, Key -> %s, value -> %s, Default value -> %s", prefName == null ? "'Default'" : prefName, prefsKey, val, defaultValue));
        return val;
    }

    public boolean readBoolean(String prefsKey, boolean defaultValue) {
        return readBoolean(null, prefsKey, defaultValue);
    }

    public boolean readBoolean(String prefsKey) {
        return readBoolean(null, prefsKey, false);
    }

    public <T> T read(String prefsKey, Type type) {
        return read(null, prefsKey, type);
    }

    public <T> T read(String prefName, String prefsKey, Type type) {
        return new Gson().fromJson(readString(prefName, prefsKey, null), type);
    }

    public void write(String prefsKey, Object prefVal) {
        write(null, prefsKey, prefVal);
    }

    public void write(String prefName, String prefsKey, Object prefVal) {
        writeString(prefName, prefsKey, new Gson().toJson(prefVal));
    }

    private SharedPreferences getPrefs(String prefName) {
        prefName = prefName == null || prefName.trim().length() == 0 ? PreferenceSpider.getInstance().getPreferenceName() : prefName;
        if (prefName == null) {
            return PreferenceManager.getDefaultSharedPreferences(mContext);
        } else {
            return mContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        }
    }

    private SharedPreferences.Editor getPrefsEditor(String prefName) {
        return getPrefs(prefName).edit();
    }

}