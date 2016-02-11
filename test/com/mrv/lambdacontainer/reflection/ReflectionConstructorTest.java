package com.mrv.lambdacontainer.reflection;

import com.mrv.lambdacontainer.ContainerFacade;
import com.mrv.lambdacontainer.TestTools.*;
import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Marcello on 11/02/2016.
 */
public class ReflectionConstructorTest {
    private ReflectionConstructor constructor;
    @Rule public final ExpectedException exception = ExpectedException.none();

    private ContainerFacade facade;

    @Before
    public void setUp() {
        facade = mock(ContainerFacade.class);
        constructor = new ReflectionConstructor(facade);
    }

    @Test
    public void testResolve() throws Exception {
        TestInterface obj = constructor.resolve(TestImplementation.class);
        assertTrue(obj instanceof TestImplementation);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveComposed()
            throws Exception {
        when(facade.resolve(TestInterface.class)).thenThrow(ClassInstantiationException.class);

        constructor.resolve(TestComposedClass1.class);

        verify(facade).resolve(TestInterface.class);
        verify(facade).resolve(TestImplementation.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveShouldThrowExceptionWhenNotPossible()
            throws Exception {
        when(facade.resolve(int.class)).thenThrow(ClassInstantiationException.class);
        exception.expect(ClassInstantiationException.class);
        constructor.resolve(TestClassPrimitive.class);
    }

}