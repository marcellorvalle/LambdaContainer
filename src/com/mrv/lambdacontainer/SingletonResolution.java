package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.interfaces.Resolution;

/**
 * Class with the ability to hold a singleton instance of type T
 */
class SingletonResolution<T>
        implements Resolution<T> {

    private final Resolution<T> internal;
    private T instance;

    SingletonResolution(Resolution<T> internal) {
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
