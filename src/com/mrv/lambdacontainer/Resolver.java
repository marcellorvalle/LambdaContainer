package com.mrv.lambdacontainer;

/**
 * Define the interface used for lambda operators to retrieve
 * one class instance.
 */
@FunctionalInterface
public interface Resolver<T> {
    /**
     * Do whatever is needed to create an instance and configure it.
     * @return
     */
    T resolve();
}
