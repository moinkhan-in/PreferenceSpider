package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.type.TypeMirror;

import static in.moinkhan.preferencespider_compiler.Imports.PREFERENCE_HELPER;

/**
 * Created by moinkhan on 26-02-2018.
 */

public class BindingField {
    private String targetParam;
    private String varName;
    private String prefKey;
    private String format;
    private TypeMirror elementKind;
    private String defaultVal;

    public BindingField(String targetParam, String varName, String prefKey, String format, TypeMirror elementKind, String defaultVal) {
        this.targetParam = targetParam;
        this.varName = varName;
        this.prefKey = prefKey;
        this.format = format;
        this.elementKind = elementKind;
        this.defaultVal = defaultVal;
    }

    public CodeBlock readBlock() {
        String argument = "java.lang.String".equals(elementKind.toString()) ? "$S" : "$L";
        return CodeBlock.of("$L.$L = $T.readPreferenceValue($L, $S, " + argument + ");\n",
                targetParam,
                varName,
                PREFERENCE_HELPER,
                targetParam,
                prefKey,
                defaultVal
        );
    }

    public CodeBlock writeBlock() {
        return CodeBlock.of("$T.writePreferenceValue($L, $S, $L.$L);\n",
                PREFERENCE_HELPER,
                targetParam,
                prefKey,
                targetParam,
                varName
        );
    }
}
