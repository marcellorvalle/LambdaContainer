package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.TestTools.TestInterface;
import org.junit.Test;
import static org.mockito.Mockito.verify;

/**
 * Created by Marcello on 15/02/2016.
 */
public class FluidResolveSingleTest extends FluidTest{
    @Override
    public void doTestWith() {
        FluidResolveSingle<TestInterface> fluid = new FluidResolveSingle<>(container, clazz);
        fluid.with(supplier);
        verify(container).addSingleResolution(clazz, supplier);
    }
}