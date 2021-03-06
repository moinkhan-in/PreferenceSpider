package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by moinkhan on 26-02-2018.
 */

public class BindingClass {

    private String targetName;
    private Set<BindingField> varList;

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setVarList(Set<BindingField> varList) {
        this.varList = varList;
    }

    public TypeSpec getClassCode(TypeElement typeElement) {

        MethodSpec.Builder readPreferenceValues = MethodSpec.methodBuilder("bindPreferenceValue");
        MethodSpec.Builder commitPreferenceValues = MethodSpec.methodBuilder("commitPreferenceValues");

        if (varList != null && varList.size() > 0) {
            readPreferenceValues.addCode(CodeBlock.of("$T prefUtils = $T.getInstance(context);\n", Imports.PREFERENCE_UTILS, Imports.PREFERENCE_UTILS));
            commitPreferenceValues.addCode(CodeBlock.of("$T prefUtils = $T.getInstance(context);\n", Imports.PREFERENCE_UTILS, Imports.PREFERENCE_UTILS));
        }

        for (BindingField bindingField : varList) {
            readPreferenceValues.addCode(bindingField.readBlock());
            commitPreferenceValues.addCode(bindingField.writeBlock());
        }

        readPreferenceValues
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Imports.CONTEXT, "context")
                .build();

        commitPreferenceValues
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Imports.CONTEXT, "context")
                .build();

        MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(typeElement), targetName.toLowerCase())
                .addCode(CodeBlock.of("this.$L = $L;\n", targetName.toLowerCase(), targetName.toLowerCase()));

        return TypeSpec.classBuilder(targetName + "_PreferenceSpider")
                .addModifiers(Modifier.FINAL)
                .addSuperinterface(Imports.PREFERENCE_BINDER)
                .addField(FieldSpec.builder(ClassName.get(typeElement), targetName.toLowerCase(), Modifier.PRIVATE).build())
                .addMethod(constructor.build())
                .addMethod(readPreferenceValues.build())
                .addMethod(commitPreferenceValues.build())
                .build();
    }
}
