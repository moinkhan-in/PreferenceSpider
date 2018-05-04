package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.ClassName;

/**
 * Created by moinkhan on 26-02-2018.
 */

public interface Imports {
    ClassName PREFERENCE_HELPER = ClassName.get("in.moinkhan.preferencespider", "PreferenceUtils");
    ClassName PREFERENCE_BINDER = ClassName.get("in.moinkhan.preferencespider", "PreferenceBinder");
}
