package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.CodeBlock;

/**
 * Created by moinkhan on 26-02-2018.
 */

public class BindingField implements Strategy {

    private String fullyName;
    private String targetParam;
    private String varName;
    private String prefName;
    private String prefKey;
    private String format;
    private Constants.Type elementType;
    private String defaultVal;
    private boolean readOnly;

    public BindingField(String fullyName, String targetClass, String varName, String prefKey, String format, Constants.Type elementKind, String defaultVal, String prefName, boolean readOnly) {
        this.fullyName = fullyName;
        this.targetParam = targetClass;
        this.varName = varName;
        this.prefKey = prefKey;
        this.format = format;
        this.elementType = elementKind;
        this.defaultVal = defaultVal;
        this.readOnly = format.trim().length() > 0 || readOnly;
        this.prefName = prefName.trim().length() > 0 ? prefName : null;
    }

    public CodeBlock readBlock() {
        switch (elementType) {
            case STRING:
                if (format.trim().length() > 0) {
                    return CodeBlock.of("$L = String.format($S, $L);\n", readExpression(), format, readFrom());
                }
                break;
        }

        return CodeBlock.of("$L = $L;\n", readExpression(), readFrom());
    }

    private CodeBlock readFrom() {

        if (elementType == Constants.Type.OTHER) {
            return CodeBlock.of(
                    "prefUtils.$L($L, $S, $L.class)",
                    readMethodName(), prefName, prefKey, fullyName
            );
        }

        return CodeBlock.of(
                "prefUtils.$L($L, $S, $L)",
                readMethodName(), prefName, prefKey, getDefaultValue()
        );
    }

    public CodeBlock writeBlock() {

        if (readOnly) {
            return CodeBlock.of("", "");
        }

        return CodeBlock.of("prefUtils.$L($L, $S, $L);\n",
                writeMethodName(),
                prefName,
                prefKey,
                readExpression()
        );
    }

    public CodeBlock readExpression() {
        switch (elementType) {
            default:
                return CodeBlock.of("$L.$L", targetParam, varName);
        }
    }

    public String readMethodName() {
        switch (elementType) {
            case BOOLEAN:
                return "readBoolean";
            case STRING:
                return "readString";
            case DOUBLE:
                return "readDouble";
            case FLOAT:
                return "readFloat";
            case LONG:
                return "readLong";
            case INT:
                return "readInt";
            case OTHER:
                return "read";
        }
        return null;
    }


    public String writeMethodName() {
        switch (elementType) {
            case BOOLEAN:
                return "writeBoolean";
            case STRING:
                return "writeString";
            case DOUBLE:
                return "writeDouble";
            case FLOAT:
                return "writeFloat";
            case LONG:
                return "writeLong";
            case INT:
                return "writeInt";
            case OTHER:
                return "write";
        }

        return null;
    }

    private String getDefaultValue() {
        if (defaultVal.trim().length() > 0) {
            return defaultVal;
        }

        switch (elementType) {
            case BOOLEAN:
                return "false";
            case STRING:
                return null;
            case DOUBLE:
            case FLOAT:
            case LONG:
            case INT:
                return "0";
        }

        return null;
    }

}
