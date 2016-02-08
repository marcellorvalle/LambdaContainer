package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.exceptions.LambdaContainerException;

import java.util.HashMap;
import java.util.Map;

/**
 * Injection container that uses Lambda operators.
 */
public class LambdaContainer {
    private final Map<Class<?>, Resolver<?>> resolvers;

    public LambdaContainer() {
        resolvers = new HashMap<>();
    }

    /**
     * Add a resolver to an specific class or interface.
     * I think this method guarantees the type check security
     * @param element a class or interface
     * @param resolver
     * @param <T> The class/interface class
     */
    public <T> void addResolver(Class<T> element, Resolver<? extends T> resolver) {
        if (resolvers.containsKey(element)) {
            throw new LambdaContainerException("Element already exists inside container: " + element.getName());
        }

        resolvers.put(element, resolver);
    }

    /**
     * Try to resolve the specified element. Suppressing unchecked warnings
     * due to the limits imposed by "addResolver".
     * @param element
     * @param <T>
     * @return instance of T.
     */
    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> element) {
        Resolver<?> resolver = resolvers.get(element);
        return (T)resolver.resolve();
    }
}
