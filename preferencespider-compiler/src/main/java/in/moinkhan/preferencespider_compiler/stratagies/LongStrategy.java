package in.moinkhan.preferencespider_compiler.stratagies;

/**
 * Created by moin on 17/7/18.
 */

public class LongStrategy implements Strategy {
    @Override
    public String readMethodName() {
        return "readLong";
    }

    @Override
    public String writeMethodName() {
        return "writeLong";
    }

    @Override
    public String getDefaultValue() {
        return "0L";
    }
}
