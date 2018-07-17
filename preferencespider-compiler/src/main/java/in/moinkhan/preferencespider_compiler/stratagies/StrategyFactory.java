package in.moinkhan.preferencespider_compiler.stratagies;

import java.util.HashMap;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by moin on 17/7/18.
 */

public class StrategyFactory {

    private static final HashMap<String, TypeKind> TYPES_KINDS = new HashMap<String, TypeKind>() {{
        put("java.lang.Long", TypeKind.LONG);
        put("java.lang.Double", TypeKind.DOUBLE);
        put("java.lang.Integer", TypeKind.INT);
        put("java.lang.Float", TypeKind.FLOAT);
        put("java.lang.Boolean", TypeKind.BOOLEAN);
    }};

    public TypeMirror dataType;

    public StrategyFactory(TypeMirror dataType) {
        this.dataType = dataType;
    }

    public Strategy getStrategy() {
        if (dataType.getKind() == TypeKind.DECLARED) {
            if (dataType.toString().equalsIgnoreCase("java.lang.String")) {
                return new StringStrategy();
            }
            return getByTypeKind(TYPES_KINDS.get(dataType.toString()));
        } else {
            return getByTypeKind(dataType.getKind());
        }
    }

    public Strategy getByTypeKind(TypeKind typeKind) {

        switch (typeKind) {
            case FLOAT:
                return new FloatStrategy();
            case DOUBLE:
                return new DoubleStrategy();
            case LONG:
                return new LongStrategy();
            case INT:
                return new IntStrategy();
            case BOOLEAN:
                return new BoolStrategy();
        }

        return new StringStrategy();
    }
}
