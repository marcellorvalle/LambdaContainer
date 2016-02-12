package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestInterface;
import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;
import com.mrv.lambdacontainer.reflection.ReflectionConstructor;
import mockit.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Marcello on 12/02/2016.
 */
public class ContainerFacadeTest {
    private final Class<TestInterface> element;
    private ContainerFacade facade;

    @Mocked
    Scenario scenario;
    @Mocked
    Container container;
    @Mocked
    ReflectionConstructor rContructor;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public ContainerFacadeTest() {
        element = TestInterface.class;

        scenario = new Scenario() {
            @Override
            protected void setResolutions() {}
        };
    }

    @Before
    public void setUp() {
        facade = new ContainerFacade();
    }

    @Test
    public void testSetScenario() {
        new Expectations() {
            { scenario.setFacade(facade); times = 1; }
        };

        facade.setScenario(scenario);
    }

    @Test
    public void testResolveFindUseMapping()
            throws ClassInstantiationException {
        new Expectations() {
            {
                container.resolutionExists(element); returns(true);
                container.resolve(element); times = 1;
                rContructor.resolve(element); times = 0;
            }
        };

        facade.resolve(element);
    }

    @Test
    public void testResolveFindUseReflection()
            throws ClassInstantiationException {
        new Expectations() {
            {
                container.resolutionExists(element); returns(false);
                container.resolve(element); times = 0;
                rContructor.resolve(element); times = 1;
            }
        };

        facade.resolve(element);
    }

    @Test
    public void testResolveNotFoundThrowsExceptions()
            throws ClassInstantiationException {
        new Expectations() {
            {
                container.resolutionExists(element); returns(false);
                container.resolve(element); times = 0;
                rContructor.resolve(element); times = 1; result = new ClassInstantiationException("");
            }
        };

        exception.expect(LambdaContainerException.class);
        facade.resolve(element);
    }

}