package in.moinkhan.preferencespider;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by moinkhan on 17-02-2018.
 */

public final class PreferenceSpider {

    private static final HashMap<Object, PreferenceBinder> binders = new HashMap<>();
    private static PreferenceSpider INSTANCE;
    private String preferenceName;
    private boolean allowLog;


    private PreferenceSpider() {

    }

    public static PreferenceSpider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreferenceSpider();
        }
        return INSTANCE;
    }

    /**
     * Preference annotated fields specified {@link Activity}.
     *
     * @param activity Target activity for preference reading.
     */
    public static void read(Activity activity) {
        getInstance().applyReadOperation(activity, activity);
    }

    /**
     * Preference annotated fields specified {@link Fragment}.
     *
     * @param fragment Target fragment for preference reading.
     */
    public static void read(Fragment fragment) {
        getInstance().applyReadOperation(fragment, fragment.getActivity());
    }

    /**
     * Preference annotated fields specified {@link android.support.v4.app.Fragment}.
     *
     * @param fragment Target activity for preference reading.
     */
    public static void read(android.support.v4.app.Fragment fragment) {
        getInstance().applyReadOperation(fragment, fragment.getContext());
    }

    /**
     * Preference annotated fields specified in your custom class.
     *
     * @param target  Target class for preference reading.
     * @param context for accessing preference.
     */
    public static void read(Object target, Context context) {
        getInstance().applyReadOperation(target, context);
    }

    /**
     * Preference annotated fields specified {@link Activity}.
     *
     * @param activity Target activity for preference writing.
     */
    public static void write(Activity activity) {
        getInstance().applyWriteOperation(activity, activity);
    }

    /**
     * Preference annotated fields specified {@link Fragment}.
     *
     * @param fragment Target fragment for preference writing.
     */
    public static void write(Fragment fragment) {
        getInstance().applyWriteOperation(fragment, fragment.getActivity());
    }

    /**
     * Preference annotated fields specified {@link android.support.v4.app.Fragment}.
     *
     * @param fragment Target activity for preference writing.
     */
    public static void write(android.support.v4.app.Fragment fragment) {
        getInstance().applyWriteOperation(fragment, fragment.getContext());
    }

    /**
     * Preference annotated fields specified in your custom class.
     *
     * @param target  Target class for preference writing.
     * @param context for accessing preference.
     */
    public static void write(Object target, Context context) {
        getInstance().applyWriteOperation(target, context);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    private void setPreferenceName(String preferenceName) {
        this.preferenceName = preferenceName;
    }

    public boolean isAllowLog() {
        return allowLog;
    }

    public void setAllowLog(boolean allowLog) {
        this.allowLog = allowLog;
    }

    private void applyReadOperation(Object target, Context context) {
        PreferenceBinder binder = getBinderFromCacheIfAvailable(target);
        if (binder != null) {
            binder.bindPreferenceValue(context);
        }
    }

    private void applyWriteOperation(Object target, Context context) {
        PreferenceBinder binder = getBinderFromCacheIfAvailable(target);
        if (binder != null) {
            binder.commitPreferenceValues(context);
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
        private boolean allowLog;

        private Builder() {
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
            PreferenceSpider instance = PreferenceSpider.getInstance();
            instance.setAllowLog(allowLog);
            instance.setPreferenceName(preferenceName);
        }
    }
}
