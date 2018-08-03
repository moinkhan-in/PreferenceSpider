package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.CodeBlock;

/**
 * Created by moin on 2/8/18.
 */

class BindingMethod {

    private String targetClass;
    private String name;
    private String[] keys;

    public BindingMethod(String name, String[] keys) {
        this.name = name;
        this.keys = keys;
    }

    public CodeBlock onChangedBlock() {

        return CodeBlock.of(
                "prefUtils.getPrefs($S).registerOnSharedPreferenceChangeListener(" +
                        "new $T.OnSharedPreferenceChangeListener() {\n" +
                        "            @Override\n" +
                        "            public void onSharedPreferenceChanged($T sharedPreferences, String key) {\n" +
                        "\n" +
                        "            }\n" +
                        "        });",
                name, Imports.PREFERENCES, Imports.PREFERENCES
        );
    }
}
