package com.mrv.lambdacontainer.TestTools;

/**
 * Simple interface implementation for testing purposes
 */
public class TestImplementation implements TestInterface {
    private boolean somethingDone = false;

    public void doSomething() {
        somethingDone = true;
    }

    public boolean hasDoneSomething() {
        return somethingDone;
    }
}
