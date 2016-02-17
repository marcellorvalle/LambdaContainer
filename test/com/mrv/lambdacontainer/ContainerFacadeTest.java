package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;
import com.mrv.lambdacontainer.reflection.Injector;
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
    private final TestImplementation implementation;
    private ContainerFacade facade;

    @Mocked
    Scenario scenario;
    @Mocked
    Container container;
    @Mocked
    ReflectionConstructor rContructor;
    @Mocked
    Injector injector;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public ContainerFacadeTest() {
        element = TestInterface.class;
        implementation = new TestImplementation();

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
        new Expectations() {{
                container.resolutionExists(element); returns(true);
                container.resolve(element); times = 1;
                rContructor.resolve(element); times = 0;
                injector.inject(implementation, element); times = 0;
        }};

        facade.resolve(element);
    }

    @Test
    public void testResolveFindUseReflection()
            throws ClassInstantiationException {
        new Expectations() {{
                container.resolutionExists(element); returns(false);
                container.resolve(element); times = 0;
                rContructor.resolve(element); result = implementation; times = 1;
                injector.inject(implementation, element); times = 1;
        }};

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