package in.moinkhan.preferencespider_compiler.strategies;

import com.squareup.javapoet.CodeBlock;

import in.moinkhan.preferencespider_compiler.Constants;

/**
 * Created by moin on 23/7/18.
 */

public class TypeStrategy {


    private final String fullyName;
    private final String prefKey;
    private final String format;
    private final Constants.DataType elementKind;
    private final String defaultVal;
    private final String prefName;

    public TypeStrategy(String fullyName, String prefKey, String format, Constants.DataType elementKind, String defaultVal, String prefName) {
        this.fullyName = fullyName;
        this.prefKey = prefKey;
        this.format = format;
        this.elementKind = elementKind;
        this.defaultVal = defaultVal;
        this.prefName = prefName;
    }

    private String readMethodName() {
        switch (elementKind) {
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


    private String writeMethodName() {
        switch (elementKind) {
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
            if (elementKind == Constants.DataType.STRING) {
                return String.format("\"%s\"", defaultVal);
            } else {
                return defaultVal;
            }
        }

        switch (elementKind) {
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

    public CodeBlock readStatement() {
        if (elementKind == Constants.DataType.OTHER) {
            return CodeBlock.of(
                    "prefUtils.$L($S, $S, $L.class)",
                    readMethodName(), prefName, prefKey, fullyName
            );
        }

        CodeBlock readStatement = CodeBlock.of(
                "prefUtils.$L($S, $S, $L)",
                readMethodName(), prefName, prefKey, getDefaultValue()
        );


        switch (elementKind) {
            case STRING:
                if (format.trim().length() > 0) {
                    return CodeBlock.of("String.format($S, $L)", format, readStatement);
                }
                break;
        }

        return readStatement;
    }

    public CodeBlock writeStatement(String variable) {
        return CodeBlock.of("prefUtils.$L($S, $S, $L);\n",
                writeMethodName(),
                prefName,
                prefKey,
                variable
        );
    }
}
