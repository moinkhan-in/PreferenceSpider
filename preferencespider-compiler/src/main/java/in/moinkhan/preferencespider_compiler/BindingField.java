package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.CodeBlock;

import in.moinkhan.preferencespider_compiler.strategies.TypeStrategy;

/**
 * Created by moinkhan on 26-02-2018.
 */

public class BindingField {

    private String targetVar;
    private Constants.FieldType fieldType;
    private boolean readOnly;
    private TypeStrategy typeStrategy;

    public BindingField(String fullyName, String targetVar, String prefKey, String format, Constants.FieldType fieldType, Constants.DataType dataType, String defaultVal, String prefName, boolean readOnly) {
        this.targetVar = targetVar;
        this.fieldType = fieldType;
        this.readOnly = format.trim().length() > 0 || readOnly;
        this.typeStrategy = new TypeStrategy(fullyName, prefKey, format, dataType, defaultVal, prefName.trim().length() > 0 ? prefName : null);
    }

    public CodeBlock readBlock() {
        switch (fieldType) {
            case TEXT_VIEW:
                return CodeBlock.of("$L.setText($L);\n", targetVar, typeStrategy.readStatement());
            case COMPOUND_BUTTON:
                return CodeBlock.of("$L.setChecked($L);\n", targetVar, typeStrategy.readStatement());
            case PROGRESS_BAR:
            case SEEK_BAR:
                return CodeBlock.of("$L.setProgress($L);\n", targetVar, typeStrategy.readStatement());
            case RATING_BAR:
                return CodeBlock.of("$L.setRating($L);\n", targetVar, typeStrategy.readStatement());
            case ADAPTER_VIEW:
            case SPINNER:
                return CodeBlock.of("$L.setSelection($L);\n", targetVar, typeStrategy.readStatement());
            case NUMBER_PICKER:
                return CodeBlock.of("$L.setValue($L);\n", targetVar, typeStrategy.readStatement());
            case TOOL_BAR:
                return CodeBlock.of("$L.setTitle($L);\n", targetVar, typeStrategy.readStatement());
            default:
                return CodeBlock.of("$L = $L;\n", targetVar, typeStrategy.readStatement());
        }
    }

    public CodeBlock writeBlock() {

        if (readOnly) {
            return CodeBlock.of("", "");
        }
        switch (fieldType) {
            case TEXT_VIEW:
                return typeStrategy.writeStatement(String.format("%s.%s", targetVar, "getText().toString()"));
            case COMPOUND_BUTTON:
                return typeStrategy.writeStatement(String.format("%s.%s", targetVar, "isChecked()"));
            case PROGRESS_BAR:
            case SEEK_BAR:
                return typeStrategy.writeStatement(String.format("%s.%s", targetVar, "getProgress()"));
            case RATING_BAR:
                return typeStrategy.writeStatement(String.format("%s.%s", targetVar, "getRating()"));
            case ADAPTER_VIEW:
            case SPINNER:
                return typeStrategy.writeStatement(String.format("%s.%s", targetVar, "getSelectedItemPosition()"));
            case NUMBER_PICKER:
                return typeStrategy.writeStatement(String.format("%s.%s", targetVar, "getValue()"));
            case TOOL_BAR:
                return typeStrategy.writeStatement(String.format("%s.%s", targetVar, "getTitle().toString()"));
            default:
                return typeStrategy.writeStatement(targetVar);
        }

    }
}
