package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;
import com.mrv.lambdacontainer.fluid.*;

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

    protected final <T> FluidResolve<T> resolve(Class<T> clazz) {
        return new FluidResolve<>(
            facade.getContainer(),
            clazz
        );
    }

    protected final <T> FluidResolveSingle<T> resolveSingle(Class<T> clazz) {
        return new FluidResolveSingle<>(
                facade.getContainer(),
                clazz
        );
    }

    protected final <T> FluidOverride<T> override(Class<T> clazz) {
        return new FluidOverride<>(
                facade.getContainer(),
                clazz
        );
    }

    protected final <T> FluidExtend<T> extend(Class<T> clazz) {
        return new FluidExtend<>(
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

    protected final void injectInto(Object object) {
        facade.getInjector().injectInto(object);
    }
}
