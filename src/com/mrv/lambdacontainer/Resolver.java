package com.mrv.lambdacontainer;

/**
 * Created by Marcello on 07/02/2016.
 */
@FunctionalInterface
public interface Resolver<T> {
    T resolve();
}
