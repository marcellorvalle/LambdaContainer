package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
import com.mrv.lambdacontainer.interfaces.Extension;
import com.mrv.lambdacontainer.interfaces.Resolution;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Created by Marcello on 10/02/2016.
 */
public class ScenarioTest {
    private Scenario scenario;
    private Container container;

    private final Class<TestInterface> interfc;
    private final Resolution<TestInterface> resolution;
    private final Resolution<TestInterface> nullResolution;
    private final Extension<TestInterface> extension;

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
        container = mock(Container.class);
    }

    @Test
    public void testWithSimpleResolution() throws Exception {
        setupWithSimple();
        scenario.setResolutions();
        verify(container).addResolution(interfc, resolution);
    }

    @Test
    public void testWithSingletonResolution() throws Exception {
        setupWithSingleton();
        scenario.setResolutions();
        verify(container).addSingleResolution(interfc, resolution);
    }

    @Test
    public void testWithExtension() throws Exception {
        setupWithExtension();
        scenario.setResolutions();
        verify(container).addResolution(interfc, resolution);
        verify(container).extend(interfc, extension);
    }

    @Test
    public void testWithOverride() throws Exception {
        setupWithOverride();
        scenario.setResolutions();
        verify(container).addResolution(interfc, resolution);
        verify(container).override(interfc, nullResolution);
    }

    private void setupWithSimple() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                clear();
                resolve(interfc).with(resolution);
            }
        };

        scenario.setContainer(container);
    }

    private void setupWithSingleton() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).withSingleton(resolution);
            }
        };

        scenario.setContainer(container);
    }

    private void setupWithExtension() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).with(resolution);
                resolve(interfc).addExtension(extension);
            }
        };

        scenario.setContainer(container);
    }

    private void setupWithOverride() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).with(resolution);
                resolve(interfc).override(nullResolution);
            }
        };

        scenario.setContainer(container);
    }
}