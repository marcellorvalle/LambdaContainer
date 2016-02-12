package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;
import com.mrv.lambdacontainer.interfaces.Extension;
import com.mrv.lambdacontainer.interfaces.Resolution;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Marcello on 10/02/2016.
 */
public class ScenarioTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Scenario scenario;
    private ContainerFacade facade;

    private final Class<TestInterface> interfc;
    private final Resolution<TestInterface> resolution;
    private final Resolution<TestInterface> nullResolution;
    private final Extension<TestInterface> extension;

    @Mocked Container container;

    public ScenarioTest() {
        interfc = TestInterface.class;
        resolution = () -> new TestImplementation();
        nullResolution = () -> null;
        extension = (original -> {
            ((TestImplementation)original).doSomething();
            return original;
        });
    }

    @Before
    public void setUp() {
        facade = new ContainerFacade();
    }

    @Test
    public void testWithSimpleResolution() throws Exception {
        setupWithSimple();
        new Expectations() {{
            container.addResolution(interfc, resolution); times = 1;
        }};

        scenario.setResolutions();
    }

    @Test
    public void testWithSingletonResolution() throws Exception {
        setupWithSingleton();
        new Expectations() {{
            container.addSingleResolution(interfc, resolution); times = 1;
        }};

        scenario.setResolutions();
    }

    @Test
    public void testWithExtension() throws Exception {
        setupWithExtension();
        new Expectations() {{
            container.addResolution(interfc, resolution); times = 1;
            container.extend(interfc, extension); times = 1;
        }};

        scenario.setResolutions();
    }

    @Test
    public void testWithOverride() throws Exception {
        setupWithOverride();
        new Expectations() {{
            container.addResolution(interfc, resolution); times = 1;
            container.override(interfc, nullResolution); times = 1;
        }};

        scenario.setResolutions();
    }

    @Test
    public void testGet() throws ClassInstantiationException {
        Scenario scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                if (solutionExists(interfc)) {
                    get(interfc);
                }
            }
        };

        new Expectations(){{
            container.resolutionExists(interfc);
            returns(true);
            times = 1;

            container.resolve(interfc);
            returns(resolution.resolve());
            times = 1;
        }};

        scenario.setFacade(facade);
        scenario.setResolutions();
    }

    @Test
    public void testGetThrowsExceptionWhenNotFound() throws ClassInstantiationException {
        Scenario scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                get(interfc);
            }
        };

        new Expectations(){{
            container.resolve(interfc);
            result = new ClassInstantiationException("");
        }};

        scenario.setFacade(facade);

        exception.expect(LambdaContainerException.class);
        scenario.setResolutions();
    }

    private void setupWithSimple() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                clear();
                resolve(interfc).with(resolution);
            }
        };

        scenario.setFacade(facade);
    }

    private void setupWithSingleton() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).withSingleton(resolution);
            }
        };

        scenario.setFacade(facade);
    }

    private void setupWithExtension() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).with(resolution);
                resolve(interfc).addExtension(extension);
            }
        };

        scenario.setFacade(facade);
    }

    private void setupWithOverride() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).with(resolution);
                resolve(interfc).override(nullResolution);
            }
        };

        scenario.setFacade(facade);
    }
}