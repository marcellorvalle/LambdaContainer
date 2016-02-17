package com.mrv.lambdacontainer.TestTools;

import com.mrv.lambdacontainer.Scenario;

/**
 * Created by Marcello on 17/02/2016.
 */
public class ComplexScenario extends Scenario {
    @Override
    protected void setResolutions() {
        clear();
        resolveSingle(TestInterface.class).with(TestImplementation::new);

        resolve(TestComposedClass1.class)
            .with( () -> new TestComposedClass1(get(TestInterface.class)));

        extend(TestInterface.class)
                .with((original) -> {
                    ((TestImplementation)original).doSomething();
                    return original;
                });
    }
}
