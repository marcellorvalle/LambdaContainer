package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.*;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Some integration testing
 */
public class IntegrationTest {
    private final ContainerFacade facade;

    public IntegrationTest(){
        facade = new ContainerFacade();
    }

    @Test
    public void testComplexScenario() {
        facade.setScenario(new ComplexScenario());
        TestInterface interfc = facade.resolve(TestInterface.class);
        assertTrue(((TestImplementation)interfc).hasDoneSomething());

        ClassToInject injected = facade.resolve(ClassToInject.class);
        assertTrue(injected.getPrivateInjectionComposed() instanceof TestComposedClass1);
        assertSame(interfc, injected.getPrivate());
        assertSame(interfc, injected.publicInjection);

        TestComposedClass1 composed = (TestComposedClass1) injected.getPrivateInjectionComposed();
        assertSame(interfc, composed.getInternal());
    }

}
