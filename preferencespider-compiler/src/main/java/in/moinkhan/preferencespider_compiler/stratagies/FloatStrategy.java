package in.moinkhan.preferencespider_compiler.stratagies;

/**
 * Created by moin on 17/7/18.
 */

public class FloatStrategy implements Strategy {
    @Override
    public String readMethodName() {
        return "readFloat";
    }

    @Override
    public String writeMethodName() {
        return "writeFloat";
    }

    @Override
    public String getDefaultValue() {
        return "0f";
    }
}
