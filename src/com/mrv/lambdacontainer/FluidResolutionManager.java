package com.mrv.lambdacontainer;

import java.util.function.*;

/**
 * Auxiliary class used to give fluidity to the interface.
 */
public class FluidResolutionManager<T> {
    private final Container container;
    private final Class<T> element;

    /**
     * Constructor with container and element to apply resolution.
     * @param container
     * @param element
     */
    FluidResolutionManager(Container container, Class<T> element) {
        this.container = container;
        this.element = element;
    }

    /**
     * Will delegate a simple resolution to the container.
     * @param resolution
     */
    public void with(Supplier<? extends T> resolution) {
        container.addResolution(element, resolution);
    }

    /**
     * Will delegate a singleton resolution to the container.
     * @param resolution
     */
    public void withSingleton(Supplier<? extends T> resolution) {
        container.addSingleResolution(element, resolution);
    }

    /**
     * Will delegate the extension creation to the container.
     * @param extension
     */
    public void addExtension(UnaryOperator<T> extension) {
        container.extend(element, extension);
    }

    /**
     * Will delegate the the override to the container.
     * @param resolution
     */
    public void override(Supplier<? extends T> resolution) {
        container.override(element, resolution);
    }
}
