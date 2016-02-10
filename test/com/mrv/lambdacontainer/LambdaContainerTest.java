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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void testSetScenario() {
        Scenario scenario = mock(Scenario.class);
        cont.setScenario(scenario);

        verify(scenario).setContainer(cont);
        verify(scenario).setResolutions();
    }

    @Test
    public void testCreateDefaultInstanceSimple() throws ClassInstantiationException {
        TestInterface interfc = cont.createDefaultInstance(TestImplementation.class);
        assertTrue(interfc instanceof TestImplementation);
    }

    @Test
    public void testCreateDefaultInstanceComposedMapping() throws ClassInstantiationException {
        cont.addResolution(
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
        cont.addResolution(void.class, () -> null);
        exception.expect(LambdaContainerException.class);
        cont.addResolution(void.class, () -> null);
    }

    @Test
    public void testResolveThrowsExceptionWhenFail() {
        exception.expect(LambdaContainerException.class);
        cont.resolve(TestClassPrimitive.class);
    }

    @Test
    public void testResolution() {
        cont.addResolution(
                TestInterface.class,
                () -> new TestImplementation()
        );

        TestInterface implementation = cont.resolve(TestInterface.class);
        assertTrue(implementation instanceof TestImplementation);

        //two different instances
        assertNotSame(
                implementation,
                cont.resolve(TestInterface.class)
        );
    }

    /**
     * A little bit on integration testing here...
     */
    @Test
    public void testExtend() {
        TestImplementation impl = mock(TestImplementation.class);

        //add some indirection
        cont.addResolution(
                TestInterface.class,
                () -> cont.resolve(TestImplementation.class)
        );

        cont.addResolution(
                TestImplementation.class,
                () -> impl
        );

        cont.extend(
                TestImplementation.class,
                (original) -> {
                    //(...) extension methods
                    original.doSomething();
                    return original;
                }
        );

        cont.resolve(TestInterface.class);
        verify(impl).doSomething();
    }

    @Test
    public void testExtendThrowsExceptionWhenClassDefinitionNotFound() {
        exception.expect(LambdaContainerException.class);
        cont.extend(
                TestInterface.class,
                (original) -> original
        );
    }

    @Test
    public void testClear() {
        cont.addResolution(
                TestInterface.class,
                () -> new TestImplementation()
        );

        cont.resolve(TestInterface.class);
        cont.clear();

        exception.expect(LambdaContainerException.class);
        cont.resolve(TestInterface.class);
    }
}