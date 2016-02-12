package com.mrv.lambdacontainer;

import com.mrv.lambdacontainer.interfaces.Extension;
import com.mrv.lambdacontainer.interfaces.Resolution;

/**
 * This class is used to extend the behavior needed to object instantiation.
 */
public class Extender<T> implements Resolution<T> {
    private final Resolution<T> internal;
    private final Extension<T> extension;

     /**
     * Define the inner Resolution and extensions applicable to it.
     * @param internal
     * @param extension
     */
    Extender(Resolution<T> internal, Extension<T> extension) {
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
