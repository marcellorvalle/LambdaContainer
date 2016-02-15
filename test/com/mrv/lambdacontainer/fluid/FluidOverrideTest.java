package com.mrv.lambdacontainer.fluid;


import com.mrv.lambdacontainer.TestTools.TestInterface;
import org.junit.Test;

import static org.mockito.Mockito.verify;

/**
 * Created by Marcello on 15/02/2016.
 */
public class FluidOverrideTest extends FluidTest{

    @Override
    public void doTestWith() {
        FluidOverride<TestInterface> fluid = new FluidOverride<>(container, clazz);
        fluid.with(supplier);
        verify(container).override(clazz, supplier);
    }
}