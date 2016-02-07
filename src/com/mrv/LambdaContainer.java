package com.mrv;

import java.util.HashMap;
import java.util.Map;

/**
 * Injection container that uses Lambda operators.
 */
public class LambdaContainer {
    private final Map<Class<?>, Resolver<?>> resolvers;

    public LambdaContainer() {
        resolvers = new HashMap<Class<?>, Resolver<?>>();
    }

    public <T> void addResolver(Class<T> element, Resolver<T> resolver) {
        if (resolvers.containsKey(element)) {
            throw new RuntimeException("Element already exists inside container: " + element.getName());
        }

        resolvers.put(element, resolver);
    }

    public <T> T resolve(Class<T> element) {
        Resolver<?> resolver = resolvers.get(element);
        return (T)resolver.resolve();
    }
}
