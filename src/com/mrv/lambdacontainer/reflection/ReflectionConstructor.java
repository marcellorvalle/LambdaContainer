package com.mrv.lambdacontainer.reflection;

import com.mrv.lambdacontainer.ContainerFacade;
import com.mrv.lambdacontainer.exceptions.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will hold the responsibility to instantiate an object using reflection.
 *
 */
public class ReflectionConstructor {
    private final ContainerFacade facade;


    public ReflectionConstructor(ContainerFacade facade) {
        this.facade = facade;
    }

    /**
     * Try to create an instance of a non-mapped class. This method will search
     * for a default constructor with no parameters. If it does not exists then
     * it will be attempted to recursively instantiate all parameters.
     * @param clazz
     * @param <T>
     * @return
     * @throws ClassInstantiationException When it is not possible to instantiate.
     */
    @SuppressWarnings("unchecked")
    public <T> T resolve(Class<T> clazz)
            throws ClassInstantiationException {
        Constructor<?>[] allConstructors = clazz.getConstructors();

        for (Constructor<?> ctor : allConstructors) {
            try {
                return tryInstantiate((Constructor<T>) ctor);
            } catch (ClassInstantiationException ex) {
                continue;
            }
        }

        throw new ClassInstantiationException("Could not instantiate " + clazz.getName());
    }

    /**
     * Try to assembly all parameters needed to instantiate an
     * object using the given constructor. Will delegate parameter
     * instantiation to the facade.
     *
     * @param ctor constructor
     * @param <T> The class type
     * @return instantiated object
     * @throws ClassInstantiationException If was not able to retrieve
     * all parameters needed by the constructor.
     */
    private <T> T tryInstantiate(Constructor<T> ctor)
            throws ClassInstantiationException {
        Class<?>[] pTypes = ctor.getParameterTypes();
        List<Object> parameters = new ArrayList<>();

        for (Class<?> pType : pTypes) {
            parameters.add(facade.resolve(pType));
        }

        try {
            return ctor.newInstance(parameters.toArray());
        } catch (Exception e) {
            throw new LambdaContainerException(e.getMessage(), e);
        }
    }
}
