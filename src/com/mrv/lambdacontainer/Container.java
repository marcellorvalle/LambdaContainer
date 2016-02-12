package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.exceptions.*;
import com.mrv.lambdacontainer.interfaces.Extension;
import com.mrv.lambdacontainer.interfaces.Resolution;

import java.util.HashMap;
import java.util.Map;

/**
 * Injection container that uses Lambda operators.
 */
public class Container {
    private final Map<Class<?>, Resolution<?>> resolutions;

    /**
    * Default constructor
    */
    public Container() {
        resolutions = new HashMap<>();
    }

    /**
     * Set the current resolution scenario.
     * @param scenario
     */
    protected void setScenario(Scenario scenario) {
        scenario.setContainer(this);
        scenario.setResolutions();
    }

    /**
     * Check if the resolution exists inside the container.
     * @param element
     * @param <T>
     * @return
     */
    protected <T> boolean resolutionExists(Class<T> element) {
        return resolutions.containsKey(element);
    }

    /**
     * Add a resolution to an specific class or interface.
     * I think this method guarantees the type check security
     * @param element a class or interface
     * @param resolution
     * @param <T> The class/interface class
     * @throws LambdaContainerException If the element already exists.
     */
    protected <T> void addResolution(Class<T> element, Resolution<? extends T> resolution) {
        if (resolutions.containsKey(element)) {
            StringBuilder sb = new StringBuilder("Element already exists inside container: ").
            append(element.getName()).
            append(". Use the override function if you want to explicity change the resolution.");

            throw new LambdaContainerException(sb.toString());
        }

        this.override(element, resolution);
    }

    /**
     * Add a resolution to an specific class or interface. There will be one single
     * class instance (Singleton) during the container lifetime.
     * @param element
     * @param resolution
     * @param <T>
     */
    protected <T> void addSingleResolution(Class<T> element, Resolution<? extends T> resolution) {
        addResolution(
                element,
                new SingletonResolution<>(resolution)
        );
    }

    /**
     * Add the resolution and  overwrite the old one if it exists.
     * @param element
     * @param resolution
     * @param <T>
     */
    protected <T> void override(Class<T> element, Resolution<? extends T> resolution) {
        resolutions.put(element, resolution);
    }

    /**
     * Extend the behavior used with object instantiation.
     * @param element
     * @param extension
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    protected <T> void extend(Class<T> element, Extension<T> extension) {
        if (!resolutions.containsKey(element)) {
            throw new LambdaContainerException("Element not found inside container: " + element.getName());
        }

        Resolution<T> original = (Resolution<T>) resolutions.get(element);
        Resolution<T> extended = new Extender<>(original, extension);

        resolutions.put(element, extended);
    }

    /**
     * Clear all the defined resolutions.
     */
    protected void clear() {
        resolutions.clear();
    }

    /**
     * Try to resolve the specified element. Suppressing unchecked warnings
     * due to the limits imposed by "addResolution".
     * @param element
     * @param <T>
     * @return instance of T.
     */
    public <T> T resolve(Class<T> element) throws ClassInstantiationException {
        if (resolutions.containsKey(element)) {
            Resolution<?> resolution = resolutions.get(element);
            element.cast(resolution.resolve());
        }

        throw new ClassInstantiationException("Can not resolve " + element.getName());
    }

}
