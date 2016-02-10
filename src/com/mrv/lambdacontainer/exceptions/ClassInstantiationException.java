package com.mrv.lambdacontainer.exceptions;

/**
 * Exception used when a class can not be instantiated.
 */
public class ClassInstantiationException extends Exception {
    /**
     * Simple invocation with a message.
     * @param message
     */
    public ClassInstantiationException(String message) {
        super(message);
    }
}
