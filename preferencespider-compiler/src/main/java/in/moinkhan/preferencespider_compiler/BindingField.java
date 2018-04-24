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
    private boolean readOnly = false;

    public BindingField(String targetParam, String varName, String prefKey, String format, TypeMirror elementKind, String defaultVal) {
        this.targetParam = targetParam;
        this.varName = varName;
        this.prefKey = prefKey;
        this.format = format;
        this.elementKind = elementKind;
        this.defaultVal = defaultVal;
        this.readOnly = isStringFormatApplicable();
    }

    public CodeBlock readBlock() {
        String argument = "java.lang.String".equals(elementKind.toString()) ? "$S" : "$L";
        String variable = "$L.$L";
        String assignmentOperator = " = ";
        String expression = "$T.readPreferenceValue($L, $S, " + argument + ")";
        String formatWrapper = "String.format(\"" + format + "\", " + expression + ")";

        if (isStringFormatApplicable()) {
            expression = formatWrapper;
        }

        return CodeBlock.of(variable + assignmentOperator + expression + ";\n",
                targetParam,
                varName,
                PREFERENCE_HELPER,
                targetParam,
                prefKey,
                defaultVal
        );
    }

    public CodeBlock writeBlock() {

        if (readOnly) {
            return CodeBlock.of("", "");
        }

        return CodeBlock.of("$T.writePreferenceValue($L, $S, $L.$L);\n",
                PREFERENCE_HELPER,
                targetParam,
                prefKey,
                targetParam,
                varName
        );
    }

    private boolean isStringFormatApplicable() {
        return format != null && format.length() > 0;
    }
}
