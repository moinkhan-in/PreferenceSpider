package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.CodeBlock;

import in.moinkhan.preferencespider_compiler.strategies.TypeStrategy;

/**
 * Created by moinkhan on 26-02-2018.
 */

public class BindingField {

    private String targetVar;
    private boolean readOnly;
    private TypeStrategy typeStrategy;

    public BindingField(String fullyName, String targetVar, String prefKey, String format, Constants.Type elementKind, String defaultVal, String prefName, boolean readOnly) {
        this.targetVar = targetVar;
        this.readOnly = format.trim().length() > 0 || readOnly;
        this.typeStrategy = new TypeStrategy(fullyName, prefKey, format, elementKind, defaultVal, prefName.trim().length() > 0 ? prefName : null);
    }

    public CodeBlock readBlock() {
        return CodeBlock.of("$L = $L;\n", targetVar, typeStrategy.readStatement());
    }

    public CodeBlock writeBlock() {

        if (readOnly) {
            return CodeBlock.of("", "");
        }

        return typeStrategy.writeStatement(targetVar);
    }

}
