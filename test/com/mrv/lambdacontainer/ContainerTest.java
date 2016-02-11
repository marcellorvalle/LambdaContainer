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
public class ContainerTest {
    private Container cont;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        cont = new Container();
    }

    @Test
    public void testSetScenario() {
        Scenario scenario = mock(Scenario.class);
        cont.setScenario(scenario);

        verify(scenario).setContainer(cont);
        verify(scenario).setResolutions();
    }

    @Test
    public void testAddResolverThrowsExceptionWhenDuplicated() {
        cont.addResolution(void.class, () -> null);
        exception.expect(LambdaContainerException.class);
        cont.addResolution(void.class, () -> null);
    }

    @Test
    public void testResolveThrowsExceptionWhenFail()
            throws Exception {
        exception.expect(ClassInstantiationException.class);
        cont.resolve(TestClassPrimitive.class);
    }

    @Test
    public void testResolution()
            throws Exception {
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
    public void testExtend()
            throws Exception {
        TestImplementation impl = mock(TestImplementation.class);

        cont.addResolution(
                TestInterface.class,
                () -> impl
        );

        cont.extend(
                TestInterface.class,
                (original) -> {
                    //(...) extension methods
                    ((TestImplementation)original).doSomething();
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
    public void testClear()
            throws Exception {
        cont.addResolution(
                TestInterface.class,
                () -> new TestImplementation()
        );

        cont.resolve(TestInterface.class);
        cont.clear();

        exception.expect(ClassInstantiationException.class);
        cont.resolve(TestInterface.class);
    }
}