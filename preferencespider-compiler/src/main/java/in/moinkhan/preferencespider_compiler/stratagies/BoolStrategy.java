package in.moinkhan.preferencespider_compiler.stratagies;

/**
 * Created by moin on 17/7/18.
 */

public class BoolStrategy implements Strategy {
    @Override
    public String readMethodName() {
        return "readBoolean";
    }

    @Override
    public String writeMethodName() {
        return "writeBoolean";
    }

    @Override
    public String getDefaultValue() {
        return "false";
    }
}
