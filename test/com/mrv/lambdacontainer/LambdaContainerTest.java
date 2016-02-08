package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestClassPrimitive;
import com.mrv.lambdacontainer.TestTools.TestComposedClass1;
import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;
import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
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
    public void testCreateDefaultInstanceSimple() throws ClassInstantiationException {
        TestInterface interfc = cont.createDefaultInstance(TestImplementation.class);
        assertTrue(interfc instanceof TestImplementation);
    }

    @Test
    public void testCreateDefaultInstanceComposedMapping() throws ClassInstantiationException {
        cont.addResolver(
                TestInterface.class,
                () -> new TestImplementation()
        );

        TestComposedClass1 composed = cont.createDefaultInstance(TestComposedClass1.class);
        assertTrue(composed.getInternal() instanceof TestImplementation);
        assertEquals(TestComposedClass1.INTERFACE_CONSTRUCTOR, composed.constructorUsed);
    }

    @Test
    public void testCreateDefaultInstanceComposedNoMapping() throws ClassInstantiationException {
        TestComposedClass1 composed = cont.createDefaultInstance(TestComposedClass1.class);
        assertTrue(composed.getInternal() instanceof TestImplementation);
        assertEquals(TestComposedClass1.IMPLEMENTATION_CONSTRUCTOR, composed.constructorUsed);
    }

    @Test
    public void testCreateDefaultInstanceCanNotInstantiateInterface() throws ClassInstantiationException {
        exception.expect(ClassInstantiationException.class);
        cont.createDefaultInstance(TestInterface.class);
    }

    @Test
    public void testCreateDefaultInstanceCanNotPrimitiveOnConstructor() throws ClassInstantiationException {
        exception.expect(ClassInstantiationException.class);
        cont.createDefaultInstance(TestClassPrimitive.class);
    }

    @Test
    public void testAddResolverThrowsExceptionWhenDuplicated() {
        cont.addResolver(void.class, () -> null);
        exception.expect(LambdaContainerException.class);
        cont.addResolver(void.class, () -> null);
    }

    @Test
    public void testResolveThrowsExceptionWhenFail() {
        exception.expect(LambdaContainerException.class);
        cont.resolve(TestClassPrimitive.class);
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