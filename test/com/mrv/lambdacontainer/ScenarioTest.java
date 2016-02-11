package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
import com.mrv.lambdacontainer.interfaces.Extension;
import com.mrv.lambdacontainer.interfaces.Resolver;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Created by Marcello on 10/02/2016.
 */
public class ScenarioTest {
    private Scenario scenario;
    private LambdaContainer container;

    private final Class<TestInterface> interfc;
    private final Resolver<TestInterface> resolver;
    private final Resolver<TestInterface> nullResolver;
    private final Extension<TestInterface> extension;

    public ScenarioTest() {
        interfc = TestInterface.class;
        resolver = () -> new TestImplementation();
        nullResolver = () -> null;
        extension = (original -> {
            ((TestImplementation)original).doSomething();
            return original;
        });
    }

    @Before
    public void setUp() {
        container = mock(LambdaContainer.class);
    }

    @Test
    public void testWithSimpleResolution() throws Exception {
        setupWithSimple();
        scenario.setResolutions();
        verify(container).addResolution(interfc, resolver);
    }

    @Test
    public void testWithSingletonResolution() throws Exception {
        setupWithSingleton();
        scenario.setResolutions();
        verify(container).addSingleResolution(interfc, resolver);
    }

    @Test
    public void testWithExtension() throws Exception {
        setupWithExtension();
        scenario.setResolutions();
        verify(container).addResolution(interfc, resolver);
        verify(container).extend(interfc, extension);
    }

    @Test
    public void testWithOverride() throws Exception {
        setupWithOverride();
        scenario.setResolutions();
        verify(container).addResolution(interfc, resolver);
        verify(container).override(interfc, nullResolver);
    }

    private void setupWithSimple() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                clear();
                resolve(interfc).with(resolver);
            }
        };

        scenario.setContainer(container);
    }

    private void setupWithSingleton() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).withSingleton(resolver);
            }
        };

        scenario.setContainer(container);
    }

    private void setupWithExtension() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).with(resolver);
                resolve(interfc).addExtension(extension);
            }
        };

        scenario.setContainer(container);
    }

    private void setupWithOverride() {
        scenario = new Scenario() {
            @Override
            protected void setResolutions() {
                resolve(interfc).with(resolver);
                resolve(interfc).override(nullResolver);
            }
        };

        scenario.setContainer(container);
    }
}