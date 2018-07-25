package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import in.moinkhan.preferencespider_annotations.Preference;
import in.moinkhan.preferencespider_annotations.PreferenceName;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

public class PreferenceSpiderCompiler extends AbstractProcessor {

    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            annotations.add(annotation.getCanonicalName());
        }

        return annotations;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new HashSet<>();
        annotations.add(Preference.class);
        annotations.add(PreferenceName.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        boolean hasError = false;

        // Preference field validations
        Set<? extends Element> preferenceElement = roundEnvironment.getElementsAnnotatedWith(Preference.class);
        for (Element element : preferenceElement) {
            if (isInaccessibleViaGeneratedCode(Preference.class, "field", element)) {
                hasError = true;
            }
        }

        // collecting all @Preference annotations.
        Map<TypeElement, Set<Element>> mapElements = new LinkedHashMap<>();
        for (Element element : preferenceElement) {
            TypeElement classType = (TypeElement) element.getEnclosingElement();
            if (!mapElements.containsKey(classType)) {
                mapElements.put(classType, new LinkedHashSet<Element>());
            }
            mapElements.get(classType).add(element);
        }

        if (hasError) {
            return false;
        }

        processElement(mapElements);
        return true;
    }

    private void processElement(Map<TypeElement, Set<Element>> mapElements) {
        for (Map.Entry<TypeElement, Set<Element>> entry : mapElements.entrySet()) {

            TypeElement classElement = entry.getKey();
            final String className = classElement.getSimpleName().toString();

            BindingClass bindingClass = new BindingClass();
            bindingClass.setTargetName(className);

            Set<BindingField> models = new HashSet<>();
            bindingClass.setVarList(models);

            for (Element prefElement : entry.getValue()) {

                Preference prefAnnotation = prefElement.getAnnotation(Preference.class);

                String varName = prefElement.getSimpleName().toString();
                String key = prefAnnotation.key().length() > 0 ? prefAnnotation.key() : varName;

                String preferenceName = prefAnnotation.name();
                if (preferenceName.trim().length() <= 0) {
                    preferenceName = getPreferenceNameFromClass(prefElement);
                }

                Constants.DataType dataType = getDataTypeByTypeMirror(prefElement.asType());
                Constants.FieldType fieldType = getFieldTypeByTypeMirror(prefElement.asType());

                showMessage(dataType.name() + " -> " + fieldType.name());
                models.add(new BindingField(
                        prefElement.asType().toString(),
                        String.format("%s.%s", className.toLowerCase(), varName),
                        key,
                        prefAnnotation.format(),
                        fieldType,
                        dataType,
                        prefAnnotation.defaultValue(),
                        preferenceName,
                        prefAnnotation.readOnly()
                ));
            }

            TypeSpec typeSpec = bindingClass.getClassCode(classElement);
            JavaFile file = JavaFile.builder(elementUtils.getPackageOf(classElement).toString(), typeSpec)
                    .build();

            try {
                file.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                error(classElement, "%s cannot write", classElement.getSimpleName());
            }
        }
    }

    private String getPreferenceNameFromClass(Element prefElement) {
        PreferenceName prefNameAnnotation = prefElement.getEnclosingElement().getAnnotation(PreferenceName.class);
        String preferenceName = "";
        if (prefNameAnnotation != null) {
            preferenceName = prefNameAnnotation.value();
            if (preferenceName.trim().length() <= 0) {
                preferenceName = prefElement.getEnclosingElement().asType().toString();
            }
        }

        return preferenceName;
    }

    private boolean isInaccessibleViaGeneratedCode(Class<? extends Annotation> annotationClass, String targetThing, Element element) {
        boolean hasError = false;
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        // Verify method modifiers.
        Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(PRIVATE) || modifiers.contains(STATIC)) {
            error(element, "@%s %s must not be private or static. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        // Verify containing type.
        if (enclosingElement.getKind() != CLASS) {
            error(enclosingElement, "@%s %s may only be contained in classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        // Verify containing class visibility is not private.
        if (enclosingElement.getModifiers().contains(PRIVATE)) {
            error(enclosingElement, "@%s %s may not be contained in private classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        // verify is it from supported type.

        // verify default value
        Preference preference = (Preference) element.getAnnotation(annotationClass);
        String defaultValue = preference.defaultValue();
        String format = preference.format();

        Constants.DataType dataType = getDataTypeByTypeMirror(element.asType());

        boolean isFormatApplicable = isFormatApplicable(format);
        if (isFormatApplicable && dataType != Constants.DataType.STRING) {
            error(enclosingElement, "Format is only applicable in string preference. (%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
            hasError = true;
        }

        if (defaultValue.length() > 0) {
            if (dataType == Constants.DataType.BOOLEAN) {
                boolean isValid = isValidBoolean(defaultValue);
                if (!isValid) {
                    error(enclosingElement, "Default value of %s %s is not valid boolean. (%s.%s)", annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                    hasError = true;
                }
            } else if (dataType == Constants.DataType.INT) {
                boolean isValid = isValidInt(defaultValue);
                if (!isValid) {
                    error(enclosingElement, "Default value of %s %s is not valid integer. (%s.%s)", annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                    hasError = true;
                }
            } else if (dataType == Constants.DataType.LONG) {
                boolean isValid = isValidLong(defaultValue);
                if (!isValid) {
                    error(enclosingElement, "Default value of %s %s is not valid long. (%s.%s)", annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                    hasError = true;
                }
            } else if (dataType == Constants.DataType.FLOAT) {
                boolean isValid = isValidFloat(defaultValue);
                if (!isValid) {
                    error(enclosingElement, "Default value of %s %s is not valid float. (%s.%s)", annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                    hasError = true;
                }
            } else if (dataType == Constants.DataType.DOUBLE) {
                boolean isValid = isValidFloat(defaultValue);
                if (!isValid) {
                    error(enclosingElement, "Default value of %s %s is not valid double. (%s.%s)", annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                    hasError = true;
                }
            }
        }

        return hasError;
    }

    private Constants.FieldType getFieldTypeByTypeMirror(TypeMirror typeMirror) {
        if (Constants.SUPPORTED_FIELD_TYPES.containsKey(typeMirror.toString())) {
            return Constants.SUPPORTED_FIELD_TYPES.get(typeMirror.toString());
        }

        List<? extends TypeMirror> allSuperClass = typeUtils.directSupertypes(typeMirror);
        for (TypeMirror type : allSuperClass) {
            if (Constants.SUPPORTED_FIELD_TYPES.containsKey(type.toString())) {
                return Constants.SUPPORTED_FIELD_TYPES.get(type.toString());
            }
        }

        return Constants.FieldType.NONE;
    }

    private Constants.DataType getDataTypeByTypeMirror(TypeMirror dataType) {
        String strType = dataType.getKind().isPrimitive() ? dataType.getKind().toString() : dataType.toString();
        if (Constants.SUPPORTED_DATA_TYPES.containsKey(strType)) {
            return Constants.SUPPORTED_DATA_TYPES.get(strType);
        }

        Constants.FieldType fieldType = getFieldTypeByTypeMirror(dataType);
        if (fieldType != Constants.FieldType.NONE) {
            return Constants.FILED_TYPE_TO_DATA_TYPE.get(fieldType);
        }

        return Constants.DataType.OTHER;
    }

    private boolean isFormatApplicable(String format) {
        return format != null && format.trim().length() > 0;
    }

    private boolean isValidFloat(String defaultValue) {
        try {
            Float.parseFloat(defaultValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidLong(String defaultValue) {
        try {
            Long.parseLong(defaultValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidInt(String defaultValue) {
        try {
            Integer.parseInt(defaultValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidBoolean(String defaultValue) {
        return "true".equalsIgnoreCase(defaultValue) || "false".equalsIgnoreCase(defaultValue);
    }

    private void error(Element element, String message, Object... args) {
        printMessage(element, message, args);
    }

    private void showMessage(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message);
    }

    private void printMessage(Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }

        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }

}
