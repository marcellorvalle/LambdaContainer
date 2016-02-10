package com.mrv.lambdacontainer;

/**
 * Represents different resolution scenarios. Developers may extend this and override setResolutions method.
 */
public abstract class Scenario {
    private LambdaContainer container;

    protected void setContainer(LambdaContainer container) {
        this.container = container;
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
                container,
                clazz
        );
    }

    /**
     * Clear all resolutions.
     */
    protected void clear() {
        container.clear();
    }
}
