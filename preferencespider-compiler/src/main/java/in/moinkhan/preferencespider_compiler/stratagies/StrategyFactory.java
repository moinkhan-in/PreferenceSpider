package in.moinkhan.preferencespider_compiler.stratagies;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import in.moinkhan.preferencespider_compiler.Constants;

/**
 * Created by moin on 17/7/18.
 */

public class StrategyFactory {

    public TypeMirror dataType;

    public StrategyFactory(TypeMirror dataType) {
        this.dataType = dataType;
    }

    public Strategy getStrategy() {
        if (dataType.getKind() == TypeKind.DECLARED) {
            if (dataType.toString().equalsIgnoreCase("java.lang.String")) {
                return new StringStrategy();
            }
            return getByTypeKind(Constants.SUPPORTED_DECLARED_TYPES.get(dataType.toString()));
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
