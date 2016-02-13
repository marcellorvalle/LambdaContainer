package com.mrv.lambdacontainer;

import java.util.function.Supplier;

/**
 * Class with the ability to hold a singleton instance of type T
 */
class SingletonResolution<T>
        implements Supplier<T> {

    private final Supplier<T> internal;
    private T instance;

    SingletonResolution(Supplier<T> internal) {
        this.internal = internal;
    }

    /**
     * Resolution will retrieve the same instance after the first instantiation.
     * @return
     */
    @Override
    public T get() {
        if (instance == null) {
            instance = internal.get();
        }
        return instance;
    }
}
