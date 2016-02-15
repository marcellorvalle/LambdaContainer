package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.Container;
import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;

import java.util.function.Supplier;
import static org.mockito.Mockito.mock;
import org.junit.Test;


/**
 * Created by Marcello on 15/02/2016.
 */
public abstract class FluidTest {
    protected final Class<TestInterface> clazz;
    protected final Supplier<TestInterface> supplier;
    protected final Container container;

    public FluidTest() {
        clazz = TestInterface.class;
        container = mock(Container.class);
        supplier = TestImplementation::new;
    }

    @Test
    public void testSubClass() {
        doTestWith();
    }

    public abstract void doTestWith();
}
