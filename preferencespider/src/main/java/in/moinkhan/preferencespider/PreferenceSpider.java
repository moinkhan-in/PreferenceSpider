package in.moinkhan.preferencespider;

import android.app.Activity;
import android.app.Fragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by moinkhan on 17-02-2018.
 */

public class PreferenceSpider {

    private static HashMap<Object, PreferenceBinder> binders = new HashMap<>();

    public static void read(Activity activity) {
        applyReadOperation(activity);
    }

    public static void read(Fragment fragment) {
        applyReadOperation(fragment);
    }

    public static void write(Activity activity) {
        applyWriteOperation(activity);
    }

    public static void write(Fragment fragment) {
        applyWriteOperation(fragment);
    }

    private static void applyReadOperation(Object activity) {
        PreferenceBinder binder = getBinderFromCacheIfAvailable(activity);
        if (binder != null) {
            binder.bindPreferenceValue();
        }
    }

    private static void applyWriteOperation(Object target) {
        PreferenceBinder binder = getBinderFromCacheIfAvailable(target);
        if (binder != null) {
            binder.commitPreferenceValues();
        }
    }

    private static PreferenceBinder getBinderFromCacheIfAvailable(Object target) {

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
}
