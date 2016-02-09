package com.mrv.lambdacontainer;

/**
 * Class with the ability to hold a singleton instance of type T
 */
class SingletonResolver<T>
        implements Resolver<T> {

    private final Resolver<T> internal;
    private T instance = null;

    SingletonResolver(Resolver<T> internal) {
        this.internal = internal;
    }

    public T resolve() {
        if (instance == null) {
            instance = internal.resolve();
        }

        return instance;
    }
}
