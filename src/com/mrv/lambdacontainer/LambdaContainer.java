package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.exceptions.ClassInstantiationException;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Injection container that uses Lambda operators.
 */
public class LambdaContainer {
    private final Map<Class<?>, Resolver<?>> resolvers;

    /**
    * Default constructor
    */
    public LambdaContainer() {
        resolvers = new HashMap<>();
    }

    /**
     * Set the current resolution scenario.
     * @param scenario
     */
    public void setScenario(Scenario scenario) {
        scenario.setContainer(this);
        scenario.setResolutions();
    }

    /**
     * Add a resolver to an specific class or interface.
     * I think this method guarantees the type check security
     * @param element a class or interface
     * @param resolver
     * @param <T> The class/interface class
     * @throws LambdaContainerException If the element already exists.
     */
    protected <T> void addResolution(Class<T> element, Resolver<? extends T> resolver) {
        if (resolvers.containsKey(element)) {
            StringBuilder sb = new StringBuilder("Element already exists inside container: ").
            append(element.getName()).
            append(". Use the override function if you want to explicity change the resolver.");

            throw new LambdaContainerException(sb.toString());
        }

        this.override(element, resolver);
    }

    /**
     * Add a resolver to an specific class or interface. There will be one single
     * class instance (Singleton) during the container lifetime.
     * @param element
     * @param resolver
     * @param <T>
     */
    protected <T> void addSingleResolution(Class<T> element, Resolver<? extends T> resolver) {
        addResolution(
                element,
                new SingletonResolver<>(resolver)
        );
    }

    /**
     * Add the resolver and  overwrite the old one if it exists.
     * @param element
     * @param resolver
     * @param <T>
     */
    protected <T> void override(Class<T> element, Resolver<? extends T> resolver) {
        resolvers.put(element, resolver);
    }

    /**
     * Extend the behavior used with object instantiation.
     * @param element
     * @param extension
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    protected <T> void extend(Class<T> element, Extension<T> extension) {
        if (!resolvers.containsKey(element)) {
            throw new LambdaContainerException("Element not found inside container: " + element.getName());
        }

        Resolver<T> original = (Resolver<T>) resolvers.get(element);
        Resolver<T> extended = new Extender<>(original, extension);

        resolvers.put(element, extended);
    }

    /**
     * Clear all the defined resolvers.
     */
    protected void clear() {
        resolvers.clear();
    }

    /**
     * Try to resolve the specified element. Suppressing unchecked warnings
     * due to the limits imposed by "addResolution".
     * @param aClass
     * @param <T>
     * @return instance of T.
     */
    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> aClass) {
        try {
            return getInstance(aClass);
        } catch (ClassInstantiationException ex) {
            throw new LambdaContainerException(ex.getMessage(), ex);
        }
    }

    /**
     * Try to createD an instance of a non-mapped class. This method will search
     * for a default constructor with no parameters. If it does not exists then
     * it will be attempted to recursively instantiate all parameters.
     * @param aClass
     * @param <T>
     * @return
     * @throws ClassInstantiationException When it is not possible to instantiate.
     */
    @SuppressWarnings("unchecked")
    protected <T> T createDefaultInstance(Class<T> aClass)
            throws ClassInstantiationException {

        Constructor<?>[] allConstructors = aClass.getConstructors();

        for (Constructor<?> ctor : allConstructors) {
            try {
               return tryInstanciate((Constructor<T>) ctor);
            } catch (ClassInstantiationException ex) {
                continue;
            }
        }

        throw new ClassInstantiationException("Could not instantiate " + aClass.getName());
    }

    /**
     * Try to assembly all parameters needed to instantiate an
     * object using the given constructor.
     * @param ctor constructor
     * @param <T> The class type
     * @return instantiated object
     * @throws ClassInstantiationException If was not able to retrieve
     * all parameters needed by the constructor.
     */
    private <T> T tryInstanciate(Constructor<T> ctor)
            throws ClassInstantiationException {
        Class<?>[] pTypes = ctor.getParameterTypes();
        List<Object> parameters = new ArrayList<>();

        for (Class<?> pType : pTypes) {
            parameters.add(this.getInstance(pType));
        }

        try {
            return ctor.newInstance(parameters.toArray());
        } catch (Exception e) {
            throw new LambdaContainerException(e.getMessage(), e);
        }
    }

    /**
     * Try to instantiate from mapping or create a default instance if no mapping is found.
     * @param element
     * @param <T>
     * @return
     * @throws ClassInstantiationException
     */
    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class<T> element)
            throws ClassInstantiationException {
        if (resolvers.containsKey(element)) {
            Resolver<?> resolver = resolvers.get(element);
            return (T)resolver.resolve();
        }

        return createDefaultInstance(element);
    }
}
