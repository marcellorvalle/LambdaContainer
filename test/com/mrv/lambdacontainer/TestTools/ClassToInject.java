package com.mrv.lambdacontainer.TestTools;

import javax.inject.Inject;

/**
 * Simple to test injection
 */
public class ClassToInject {
    @Inject
    private TestInterface privateInjection;
    @Inject
    private TestComposedClass1 privateInjectionComposed;
    @Inject
    public TestInterface publicInjection;

    public TestInterface methodInjection;
    public TestInterface methodPrivateInjection;


    public boolean noParamaterMethodWasCalled = false;

    public TestInterface getPrivate() {
        return privateInjection;
    }

    @Inject
    public void setMethodInjection(TestInterface test) {
        this.methodInjection = test;
    }

    @Inject
    private void setMethodPrivateInjection(TestInterface test) {
        this.methodPrivateInjection = test;
    }

    @Inject
    private void noParameterMethod() {
        noParamaterMethodWasCalled = true;
    }

    public TestInterface getPrivateInjectionComposed() {
        return privateInjectionComposed;
    }
}
