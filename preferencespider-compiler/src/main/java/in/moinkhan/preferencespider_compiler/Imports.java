package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.ClassName;

/**
 * Created by moinkhan on 26-02-2018.
 */

public interface Imports {
    ClassName PREFERENCE_UTILS = ClassName.get("in.moinkhan.preferencespider", "PreferenceUtils");
    ClassName PREFERENCE_BINDER = ClassName.get("in.moinkhan.preferencespider", "PreferenceBinder");
    ClassName PREFERENCE_SPIDER = ClassName.get("in.moinkhan.preferencespider", "PreferenceSpider");
    ClassName CONTEXT = ClassName.get("android.content", "Context");
}
