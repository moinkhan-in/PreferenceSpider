package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.Modifier.STATIC;

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

        for (BindingField bindingField : varList) {
            readPreferenceValues.addCode(bindingField.readBlock());
            commitPreferenceValues.addCode(bindingField.writeBlock());
        }

        readPreferenceValues.addParameter(ClassName.get(typeElement), targetName.toLowerCase())
                .addModifiers(Modifier.PUBLIC, STATIC)
                .build();

        commitPreferenceValues.addParameter(ClassName.get(typeElement), targetName.toLowerCase())
                .addModifiers(Modifier.PUBLIC, STATIC)
                .build();

        return TypeSpec.classBuilder(targetName + "_PreferenceSpider")
                .addMethod(readPreferenceValues.build())
                .addMethod(commitPreferenceValues.build())
                .build();
    }
}
