package com.mrv.lambdacontainer;

import java.util.function.*;

/**
 * This class is used to extend the behavior needed to object instantiation.
 */
public class Extender<T> implements Supplier<T> {
    private final Supplier<T> internal;
    private final UnaryOperator<T> extension;

     /**
     * Define the inner Resolution and extensions applicable to it.
     * @param internal
     * @param extension
     */
    Extender(Supplier<T> internal, UnaryOperator<T> extension) {
        this.internal = internal;
        this.extension = extension;
    }

    /**
     * Resolution applying extension.
     * @return
     */
    @Override
    public T get() {
        T original = internal.get();
        return extension.apply(original);
    }
}
