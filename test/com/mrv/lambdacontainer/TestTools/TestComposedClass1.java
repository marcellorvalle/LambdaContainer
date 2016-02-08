package com.mrv.lambdacontainer.TestTools;

/**
 * Created by Marcello on 08/02/2016.
 */
public class TestComposedClass1 implements TestInterface{
    private TestInterface internal;

    public int constructorUsed;
    public static final int INTERFACE_CONSTRUCTOR = 0;
    public static final int IMPLEMENTATION_CONSTRUCTOR = 1;

    public TestComposedClass1(TestInterface internal) {
        this.internal = internal;
        constructorUsed = INTERFACE_CONSTRUCTOR;
    }

    public TestComposedClass1(TestImplementation internal) {
        this.internal = internal;
        constructorUsed = IMPLEMENTATION_CONSTRUCTOR;
    }

    public TestInterface getInternal() {
        return internal;
    }
}
