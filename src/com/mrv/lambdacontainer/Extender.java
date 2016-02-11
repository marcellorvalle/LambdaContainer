package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.interfaces.Extension;
import com.mrv.lambdacontainer.interfaces.Resolver;

/**
 * This class is used to extend the behavior needed to object instantiation.
 */
public class Extender<T> implements Resolver<T> {
    private final Resolver<T> internal;
    private final Extension<T> extension;

     /**
     * Define the inner Resolver and extensions applicable to it.
     * @param internal
     * @param extension
     */
    Extender(Resolver<T> internal, Extension<T> extension) {
        this.internal = internal;
        this.extension = extension;
    }

    /**
     * Resolution applying extension.
     * @return
     */
    @Override
    public T resolve() {
        T original = internal.resolve();
        return extension.extend(original);
    }
}
