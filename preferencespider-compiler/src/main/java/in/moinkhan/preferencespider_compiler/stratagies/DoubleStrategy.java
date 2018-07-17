package in.moinkhan.preferencespider_compiler.stratagies;

/**
 * Created by moin on 17/7/18.
 */

public class DoubleStrategy implements Strategy {
    @Override
    public String readMethodName() {
        return "readDouble";
    }

    @Override
    public String writeMethodName() {
        return "writeDouble";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }
}
