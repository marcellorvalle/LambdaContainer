package com.mrv.lambdacontainer.exceptions;

/**
 * Unchecked exception for situations where the program should fail.
 */
public class LambdaContainerException
        extends RuntimeException{

    /**
     * Construct with a message.
     * @param message
     */
    public LambdaContainerException(String message) {
        this(message, null);
    }

    /**
     * Construct with message and inner exception
     * @param message
     * @param inner
     */
    public LambdaContainerException(String message, Throwable inner) {
        super(message, inner);
    }

}


