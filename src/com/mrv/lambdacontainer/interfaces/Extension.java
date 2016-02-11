package com.mrv.lambdacontainer.interfaces;

/**
 * Defines a interface for lambda operations to extend instance configuration
 * before retrieve.
 */
@FunctionalInterface
public interface Extension<T> {
    /**
     * Receive T instance, do whatever needed with it and send it back.
     * @param original
     * @return Extended instance
     */
    T extend(T original);
}
