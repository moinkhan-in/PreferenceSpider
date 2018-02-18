package in.co.moinkhan.preferencespider;

import android.app.Activity;
import android.app.Fragment;
import android.preference.PreferenceManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by moinkhan on 17-02-2018.
 */

public class PreferenceSpider {

    public static void readValues(Activity activity) {
        createPreferenceBinding(activity);
    }

    public static void readValues(Fragment fragment) {
        createPreferenceBinding(fragment);
    }

    private static void createPreferenceBinding(Object activity) {
        Class<?> targetClass = activity.getClass();
        String className = targetClass.getName();
        try {
            Class<?> bindingClass = targetClass.getClassLoader().loadClass(className + "_PreferenceSpider");
            Method bindingMethod = bindingClass.getMethod("bindPreferenceValue", targetClass);
            bindingMethod.invoke(targetClass, activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void writeValues(Activity activity) {
        initiateWriteOperation(activity);
    }

    private static void initiateWriteOperation(Object target) {
        Class<?> targetClass = target.getClass();
        String className = targetClass.getName();
        try {
            Class<?> bindingClass = targetClass.getClassLoader().loadClass(className + "_PreferenceSpider");
            Method bindingMethod = bindingClass.getMethod("commitPreferenceValues", targetClass);
            bindingMethod.invoke(targetClass, target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
