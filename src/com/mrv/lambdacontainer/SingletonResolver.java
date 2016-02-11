package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.interfaces.Resolver;

/**
 * Class with the ability to hold a singleton instance of type T
 */
class SingletonResolver<T>
        implements Resolver<T> {

    private final Resolver<T> internal;
    private T instance;

    SingletonResolver(Resolver<T> internal) {
        this.internal = internal;
    }

    /**
     * Resolution will retrieve the same instance after the first instantiation.
     * @return
     */
    @Override
    public T resolve() {
        if (instance == null) {
            instance = internal.resolve();
        }
        return instance;
    }
}
