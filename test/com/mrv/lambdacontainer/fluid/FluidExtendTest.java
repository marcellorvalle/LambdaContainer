package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.TestTools.TestInterface;
import org.junit.Test;
import static org.mockito.Mockito.verify;
import java.util.function.UnaryOperator;

/**
 * Created by Marcello on 15/02/2016.
 */
public class FluidExtendTest extends FluidTest{
    private final UnaryOperator<TestInterface> unaryOp;

    public FluidExtendTest() {
        super();
        unaryOp = (original) -> original;
    }

    @Override
    public void doTestWith() {
        FluidExtend<TestInterface> fluid = new FluidExtend<>(container, clazz);
        fluid.with(unaryOp);
        verify(container).extend(clazz, unaryOp);
    }
}