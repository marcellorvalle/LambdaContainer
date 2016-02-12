package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;

/**
 * Represents different resolution scenarios. Developers may extend this and override setResolutions method.
 */
public abstract class Scenario {
    private ContainerFacade facade;

    protected void setFacade(ContainerFacade facade) {
        this.facade = facade;
    }

    /**
     * Here the developer will have the opportunity to set the resolutions.
     */
    protected abstract void setResolutions();

    /**
     * Indicates wich element should be resolved.
     * @param clazz
     * @param <T>
     * @return
     */
    protected final <T> FluidResolutionManager<T> resolve (Class<T> clazz) {
        return new FluidResolutionManager<>(
                facade.getContainer(),
                clazz
        );
    }

    /**
     * Clear all resolutions.
     */
    protected final void clear() {
        facade.getContainer().clear();
    }

    protected  final <T> boolean solutionExists(Class<T> clazz) {
        return facade.getContainer().resolutionExists(clazz);
    }

    protected final <T> T get(Class<T> clazz) {
        try {
            return facade.getContainer().resolve(clazz);
        } catch (ClassInstantiationException e) {
            throw new LambdaContainerException(e.getMessage(), e);
        }
    }
}
