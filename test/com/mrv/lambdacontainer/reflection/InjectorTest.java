package com.mrv.lambdacontainer.reflection;

import com.mrv.lambdacontainer.ContainerFacade;
import com.mrv.lambdacontainer.TestTools.ClassToInject;
import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;


/**
 * Created by Marcello on 15/02/2016.
 */
public class InjectorTest {
    private Injector injector;
    private ContainerFacade facade;

    @Before
    public void setUp(){
        facade = mock(ContainerFacade.class);
        injector = new Injector(facade);
        when(facade.resolve(TestInterface.class)).thenReturn(new TestImplementation());
    }

    @Test
    public void testProcessFields() throws Exception {
        ClassToInject cti = new ClassToInject();
        injector.processFields(cti, cti.getClass());

        assertTrue(
            "Accessible properties should be injected",
            cti.publicInjection instanceof TestImplementation
        );

        assertTrue(
            "Non accessible properties should be injected",
            cti.getPrivate() instanceof TestImplementation
        );

    }

    @Test
    public void testProcessMethods() throws Exception {
        ClassToInject cti = new ClassToInject();
        injector.processMethods(cti, cti.getClass());

        assertTrue(
                "Public methods should be injected into",
                cti.methodInjection instanceof TestImplementation
        );

        assertNull(
                "Non public methods should not be injected into",
                cti.methodPrivateInjection
        );

        assertFalse(
                "Methods with no parameters should not be called",
                cti.noParamaterMethodWasCalled
        );
    }


}