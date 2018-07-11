package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.CodeBlock;

import javax.lang.model.type.TypeMirror;

/**
 * Created by moinkhan on 26-02-2018.
 */

public class BindingField {

    private String targetParam;
    private String varName;
    private String prefName;
    private String prefKey;
    private String format;
    private TypeMirror elementKind;
    private String defaultVal;
    private boolean readOnly = false;

    public BindingField(String targetParam, String varName, String prefKey, String format, TypeMirror elementKind, String defaultVal, String prefName, boolean readOnly) {
        this.targetParam = targetParam;
        this.varName = varName;
        this.prefKey = prefKey;
        this.format = format;
        this.elementKind = elementKind;
        this.defaultVal = defaultVal;
        this.readOnly = isStringFormatApplicable() || readOnly;
        this.prefName = prefName;
    }

    public CodeBlock readBlock() {
        String argument = "java.lang.String".equals(elementKind.toString()) ? "$S" : "$L";
        String variable = "$L.$L";
        String expression = "prefUtils.readPreferenceValue($L, $S, " + argument + ")";
        String formatWrapper = "String.format(\"" + format + "\", " + expression + ")";

        if (isStringFormatApplicable()) {
            expression = formatWrapper;
        }

        return CodeBlock.of(variable + " = " + expression + ";\n",
                targetParam,
                varName,
                getPrefName(),
                prefKey,
                defaultVal
        );
    }

    public CodeBlock writeBlock() {

        if (readOnly) {
            return CodeBlock.of("", "");
        }

        return CodeBlock.of("$L.writePreferenceValue($L, $S, $L.$L);\n",
                "prefUtils",
                getPrefName(),
                prefKey,
                targetParam,
                varName
        );
    }

    private boolean isStringFormatApplicable() {
        return format != null && format.length() > 0;
    }

    public String getPrefName() {
        if (prefName == null || prefName.trim().length() == 0) {
            return CodeBlock.of("$T.getInstance(" + targetParam + ").getPreferenceName()", Imports.PREFERENCE_SPIDER).toString();
        } else {
            return "\"" + prefName + "\"";
        }
    }
}
