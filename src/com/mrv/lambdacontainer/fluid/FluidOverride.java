package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.Container;

import java.util.function.Supplier;

/**
 * Created by Marcello on 13/02/2016.
 */
public class FluidOverride<T> extends Fluid<T, Supplier<T>> {
    public FluidOverride(Container container, Class<T> clazz) {
        super(container, clazz);
    }

    @Override
    public void with(Supplier<T> lambdaOp) {
        container.override(clazz, lambdaOp);
    }
}
