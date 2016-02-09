package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.TestTools.TestImplementation;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Created by Marcello on 09/02/2016.
 */
public class ExtenderTest {

    @Test
    public void testResolve() throws Exception {
        TestImplementation impl = mock(TestImplementation.class);

        Extender<TestImplementation> ext = new Extender<>(
                () -> impl,
                (original) -> {
                    // extension calls
                    original.doSomething();
                    return original;
                }
        );

        ext.resolve();
        verify(impl).doSomething();
    }
}