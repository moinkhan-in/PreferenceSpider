package in.moinkhan.preferencespider_compiler.stratagies;

/**
 * Created by moin on 17/7/18.
 */

public class IntStrategy implements Strategy {
    @Override
    public String readMethodName() {
        return "readInt";
    }

    @Override
    public String writeMethodName() {
        return "writeInt";
    }

    @Override
    public String getDefaultValue() {
        return "0";
    }
}
