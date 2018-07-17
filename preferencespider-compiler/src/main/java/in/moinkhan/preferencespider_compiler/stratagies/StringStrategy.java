package in.moinkhan.preferencespider_compiler.stratagies;

/**
 * Created by moin on 17/7/18.
 */

public class StringStrategy implements Strategy {
    @Override
    public String readMethodName() {
        return "readString";
    }

    @Override
    public String writeMethodName() {
        return "writeString";
    }

    @Override
    public String getDefaultValue() {
        return null;
    }
}
