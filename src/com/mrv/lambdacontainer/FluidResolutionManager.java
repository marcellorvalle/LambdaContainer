package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.interfaces.Extension;
import com.mrv.lambdacontainer.interfaces.Resolver;

/**
 * Auxiliary class used to give fluidity to the interface.
 */
public class FluidResolutionManager<T> {
    private final LambdaContainer container;
    private final Class<T> element;

    /**
     * Constructor with container and element to apply resolution.
     * @param container
     * @param element
     */
    FluidResolutionManager(LambdaContainer container, Class<T> element) {
        this.container = container;
        this.element = element;
    }

    /**
     * Will delegate a simple resolution to the container.
     * @param resolver
     */
    public void with(Resolver<? extends T> resolver) {
        container.addResolution(element, resolver);
    }

    /**
     * Will delegate a singleton resolution to the container.
     * @param resolver
     */
    public void withSingleton(Resolver<? extends T> resolver) {
        container.addSingleResolution(element, resolver);
    }

    /**
     * Will delegate the extension creation to the container.
     * @param extension
     */
    public void addExtension(Extension<T> extension) {
        container.extend(element, extension);
    }

    /**
     * Will delegate the the override to the container.
     * @param resolver
     */
    public void override(Resolver<? extends T> resolver) {
        container.override(element, resolver);
    }
}
