package in.moinkhan.preferencespider_compiler;

import java.util.HashMap;

import javax.lang.model.type.TypeKind;

/**
 * Created by moin on 18/7/18.
 */

public class Constants {

    public static final HashMap<String, Type> SUPPORTED_TYPES = new HashMap<String, Type>() {{
        put(TypeKind.LONG.toString(), Type.LONG);
        put(TypeKind.DOUBLE.toString(), Type.DOUBLE);
        put(TypeKind.INT.toString(), Type.INT);
        put(TypeKind.FLOAT.toString(), Type.FLOAT);
        put(TypeKind.BOOLEAN.toString(), Type.BOOLEAN);
        put("java.lang.Long", Type.LONG);
        put("java.lang.Double", Type.DOUBLE);
        put("java.lang.Integer", Type.INT);
        put("java.lang.Float", Type.FLOAT);
        put("java.lang.Boolean", Type.BOOLEAN);
        put("java.lang.String", Type.STRING);
    }};

    public enum Type {
        LONG,
        DOUBLE,
        INT,
        FLOAT,
        BOOLEAN,
        STRING,
        OTHER,
    }
}
