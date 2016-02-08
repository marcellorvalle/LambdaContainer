package com.mrv.LambdaContainer;

import com.mrv.LambdaContainer.Exceptions.LambdaContainerException;
import com.mrv.LambdaContainer.TestTools.TestImplementation;
import com.mrv.LambdaContainer.TestTools.TestInterface;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Injection container that uses Lambda operators.
 */
public class LambdaContainerTest {
    private LambdaContainer cont;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        cont = new LambdaContainer();
    }

    @Test
    public void testAddResolverThrowsExceptionWhenDuplicated() {
        cont = new LambdaContainer();
        cont.addResolver(void.class, () -> null);
        exception.expect(LambdaContainerException.class);
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