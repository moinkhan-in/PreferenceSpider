package in.moinkhan.preferencespider_annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Preference {
    String key() default "";

    String defaultValue() default "";

    String format() default "";

    String name() default "";

    boolean readOnly() default false;
}
