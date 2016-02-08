package com.mrv.LambdaContainer;

/**
 * Created by Marcello on 07/02/2016.
 */
@FunctionalInterface
public interface Resolver<T> {
    T resolve();
}
