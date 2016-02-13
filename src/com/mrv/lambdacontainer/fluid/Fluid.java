package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.Container;

import java.util.function.*;

/**
 * Help to give fluidity to the API interface.
 */
public abstract class Fluid <T,L>{
    protected final Container container;
    protected final Class<T> clazz;

    public Fluid(Container container, Class<T> clazz) {
        this.container = container;
        this.clazz = clazz;
    }

    public abstract void with(L lambdaOp);
}
