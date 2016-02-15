package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.Container;
import java.util.function.UnaryOperator;

/**
 * Fluid extend helper.
 */
public class FluidExtend<T> extends Fluid<T, UnaryOperator<T>> {
    public FluidExtend(Container container, Class<T> clazz) {
        super(container, clazz);
    }

    @Override
    public void with(UnaryOperator<T> lambdaOp) {
        container.extend(clazz, lambdaOp);
    }
}
