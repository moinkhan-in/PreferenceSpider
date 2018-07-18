package in.moinkhan.preferencespider_compiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.type.TypeKind;

/**
 * Created by moin on 18/7/18.
 */

public class Constants {

    public static final HashMap<String, TypeKind> SUPPORTED_DECLARED_TYPES = new HashMap<String, TypeKind>() {{
        put("java.lang.Long", TypeKind.LONG);
        put("java.lang.Double", TypeKind.DOUBLE);
        put("java.lang.Integer", TypeKind.INT);
        put("java.lang.Float", TypeKind.FLOAT);
        put("java.lang.Boolean", TypeKind.BOOLEAN);
    }};
    public static final Set<String> SUPPORTED_OTHER_TYPES = new HashSet<String>() {{
        add("java.lang.Long");
        add("java.lang.Double");
        add("java.lang.Integer");
        add("java.lang.Float");
        add("java.lang.Boolean");
        add("java.lang.String");
    }};
    static final Set<TypeKind> SUPPORTED_PRIMITIVE_TYPES = new HashSet<TypeKind>() {{
        add(TypeKind.DOUBLE);
        add(TypeKind.FLOAT);
        add(TypeKind.INT);
        add(TypeKind.LONG);
        add(TypeKind.BOOLEAN);
    }};
}
