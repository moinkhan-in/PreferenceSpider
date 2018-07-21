package in.moinkhan.preferencespider_compiler;

import com.squareup.javapoet.CodeBlock;

/**
 * Created by moin on 17/7/18.
 */

public interface Strategy {

    CodeBlock readBlock();

    CodeBlock writeBlock();
}
