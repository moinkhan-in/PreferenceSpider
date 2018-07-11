package in.moinkhan.preferencespider;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.preference.PreferenceManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by moinkhan on 17-02-2018.
 */

public class PreferenceSpider {

    private static PreferenceSpider INSTANCE;
    private static final HashMap<Object, PreferenceBinder> binders = new HashMap<>();
    private Context mContext;
    private String preferenceName;
    private boolean allowLog;

    private PreferenceSpider(Context context) {
        this.mContext = context;
    }

    private void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public void setAllowLog(boolean allowLog) {
        this.allowLog = allowLog;
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public boolean isAllowLog() {
        return allowLog;
    }

    public static PreferenceSpider getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceSpider(context);
        }
        return INSTANCE;
    }

    public static void read(Activity activity) {
        getInstance(activity).applyReadOperation(activity);
    }

    public static void read(Fragment fragment) {
        getInstance(fragment.getActivity()).applyReadOperation(fragment);
    }

    public static void write(Activity activity) {
        getInstance(activity).applyWriteOperation(activity);
    }

    public static void write(Fragment fragment) {
        getInstance(fragment.getActivity()).applyWriteOperation(fragment);
    }

    private void applyReadOperation(Object activity) {
        PreferenceBinder binder = getBinderFromCacheIfAvailable(activity);
        if (binder != null) {
            binder.bindPreferenceValue();
        }
    }

    private void applyWriteOperation(Object target) {
        PreferenceBinder binder = getBinderFromCacheIfAvailable(target);
        if (binder != null) {
            binder.commitPreferenceValues();
        }
    }

    private PreferenceBinder getBinderFromCacheIfAvailable(Object target) {

        if (binders.containsKey(target)) {
            return binders.get(target);
        }

        Class<?> targetClass = target.getClass();
        String className = targetClass.getName();
        try {
            Class<PreferenceBinder> bindingClass = (Class<PreferenceBinder>) targetClass.getClassLoader().loadClass(className + "_PreferenceSpider");
            Constructor<PreferenceBinder> binderConstructor = bindingClass.getConstructor(targetClass);
            binderConstructor.setAccessible(true);
            PreferenceBinder binder = binderConstructor.newInstance(target);
            binders.put(target, binder);
            return binder;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Builder {

        private String preferenceName;
        private Context context;
        private boolean allowLog;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder preferenceName(String preferenceName) {
            this.preferenceName = preferenceName;
            return this;
        }

        public Builder allowLog(boolean allowLog) {
            this.allowLog = allowLog;
            return this;
        }

        public void build() {
            PreferenceSpider instance = PreferenceSpider.getInstance(context);
            instance.setAllowLog(allowLog);
            instance.setPreferenceName(preferenceName);
        }
    }
}
