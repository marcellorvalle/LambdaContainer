package com.mrv.lambdacontainer.exceptions;

/**
 * Unchecked exception for situations where the program should fail.
 */
public class LambdaContainerException
        extends RuntimeException{

    public LambdaContainerException(String message) {
        this(message, null);
    }

    public LambdaContainerException(String message, Throwable inner) {
        super(message, inner);
    }

}


