package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.Container;

import java.util.function.Supplier;

/**
 * Created by Marcello on 13/02/2016.
 */
public class FluidResolveSingle<T> extends Fluid<T, Supplier<T>> {
    public FluidResolveSingle(Container container, Class<T> clazz) {
        super(container, clazz);
    }

    @Override
    public void with(Supplier<T> lambdaOp) {
        container.addSingleResolution(clazz, lambdaOp);
    }
}
