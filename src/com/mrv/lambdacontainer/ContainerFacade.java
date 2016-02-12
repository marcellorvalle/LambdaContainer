package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.exceptions.*;
import com.mrv.lambdacontainer.reflection.ReflectionConstructor;

/**
 * Facade to container functionality.
 */
public class ContainerFacade {
    private final Container container;
    private final ReflectionConstructor constructor;

    public  ContainerFacade() {
        container = new Container();
        constructor = new ReflectionConstructor(this);
    }

    /**
     * Will try to resolve the instantiation using the resolutions or reflection.
     * @param element
     * @param <T>
     * @return
     * @throws ClassInstantiationException If can not instantiate the object.
     */
    public <T> T resolve(Class<T> element) {
        try {
            if (container.resolutionExists(element)) {
                return container.resolve(element);
            }

            return constructor.resolve(element);
        } catch (ClassInstantiationException e) {
            throw new LambdaContainerException(e.getMessage(), e);
        }
    }

    /**
     * Pass the scenario to the container.
     * @param scenario
     */
    public void setScenario(Scenario scenario) {
        scenario.setFacade(this);
        scenario.setResolutions();
    }

    protected Container getContainer() {
        return this.container;
    }
}
