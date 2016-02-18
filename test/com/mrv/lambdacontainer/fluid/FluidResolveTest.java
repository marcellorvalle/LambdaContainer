package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.TestTools.TestInterface;

import static org.mockito.Mockito.verify;

/**
 * Created by Marcello on 15/02/2016.
 */
public class FluidResolveTest extends FluidTest {
    @Override
    public void doTestWith() {
        FluidResolve<TestInterface> fluid = new FluidResolve<>(container, clazz);
        fluid.with(supplier);
        verify(container).addResolution(clazz, supplier);
    }
}