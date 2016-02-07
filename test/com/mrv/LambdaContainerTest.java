package com.mrv;

import com.mrv.TestTools.TestImplementation;
import com.mrv.TestTools.TestInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Injection container that uses Lambda operators.
 */
public class LambdaContainerTest {
    private LambdaContainer cont;

    @Before
    public void setUp() {
        cont = new LambdaContainer();
    }

    @Test(expected = RuntimeException.class)
    public void testAddResolverThrowsExceptionWhenDuplicated() {
        cont = new LambdaContainer();
        cont.addResolver(void.class, () -> null);
        cont.addResolver(void.class, () -> null);
    }

    @Test
    public void testResolution() {
        cont.addResolver(
                TestInterface.class,
                () -> new TestImplementation()
        );

        TestInterface interfc = cont.resolve(TestInterface.class);
        assertTrue(interfc instanceof TestImplementation);
    }
}