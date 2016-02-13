package com.mrv.lambdacontainer.fluid;

import com.mrv.lambdacontainer.Container;

import java.util.function.UnaryOperator;
/**
 * Created by Marcello on 13/02/2016.
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
