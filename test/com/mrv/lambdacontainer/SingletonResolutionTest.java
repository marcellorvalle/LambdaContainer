package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestImplementation;
import com.mrv.lambdacontainer.TestTools.TestInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Marcello on 09/02/2016.
 */
public class SingletonResolutionTest {
    private SingletonResolution<TestInterface> resolver;

    @Before
    public void setUp() throws Exception {
        resolver = new SingletonResolution<>(
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