package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Marcello on 09/02/2016.
 */
public class SingletonResolverTest {
    private SingletonResolver<TestInterface> resolver;

    @Before
    public void setUp() throws Exception {
        resolver = new SingletonResolver<>(
                () -> new TestImplementation()
        );
    }

    @Test
    public void testResolve() throws Exception {
        assertSame(
                resolver.resolve(),
                resolver.resolve()
        );
    }
}