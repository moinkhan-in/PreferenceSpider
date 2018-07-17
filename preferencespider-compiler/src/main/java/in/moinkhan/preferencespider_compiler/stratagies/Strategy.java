package in.moinkhan.preferencespider_compiler.stratagies;

/**
 * Created by moin on 17/7/18.
 */

public interface Strategy {
    String readMethodName();

    String writeMethodName();

    String getDefaultValue();
}
