package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import in.moinkhan.preferencespider_annotations.Preference;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

@SupportedAnnotationTypes("in.moinkhan.preferencespider_annotations.Preference")
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
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        boolean hasError = false;
        Set<? extends Element> preferenceElement = roundEnvironment.getElementsAnnotatedWith(Preference.class);
        for (Element element : preferenceElement) {
            if (isInaccessibleViaGeneratedCode(Preference.class, "field", element)) {
                hasError = true;
            }
        }

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


                models.add(new BindingField(
                        className.toLowerCase(),
                        prefElement.getSimpleName().toString(),
                        prefAnnotation.key(),
                        prefAnnotation.format(),
                        prefElement.asType(),
                        prefAnnotation.defaultValue()
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

    private boolean validateElement(Element prefElement) {
        return isInaccessibleViaGeneratedCode(Preference.class, "field", prefElement);
    }

    private boolean isInaccessibleViaGeneratedCode(Class<? extends Annotation> annotationClass,
                                                   String targetThing, Element element) {
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

        // verify default value
        Preference preference = (Preference) element.getAnnotation(annotationClass);
        String defaultValue = preference.defaultValue();
        TypeMirror dataType = element.asType();

        showMessage(dataType.getKind().toString() + "-> " +  Boolean.class.getPackage());
        if (dataType.getKind() == TypeKind.BOOLEAN ) {
            boolean isValid = isValidBoolean(defaultValue);
            if (!isValid) {
                error(enclosingElement, "Default value of %s %s is not valid boolean. (%s.%s)" , annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                hasError = true;
            }
        } else if (dataType.getKind() == TypeKind.INT) {
            boolean isValid = isValidInt(defaultValue);
            if (!isValid) {
                error(enclosingElement, "Default value of %s %s is not valid integer. (%s.%s)" , annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                hasError = true;
            }
        } else if (dataType.getKind() == TypeKind.LONG) {
            boolean isValid = isValidLong(defaultValue);
            if (!isValid) {
                error(enclosingElement, "Default value of %s %s is not valid long. (%s.%s)" , annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                hasError = true;
            }
        } else if (dataType.getKind() == TypeKind.FLOAT) {
            boolean isValid = isValidFloat(defaultValue);
            if (!isValid) {
                error(enclosingElement, "Default value of %s %s is not valid float. (%s.%s)" , annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(), element.getSimpleName());
                hasError = true;
            }
        }


        return hasError;
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
