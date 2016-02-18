package com.mrv.lambdacontainer.reflection;

import com.mrv.lambdacontainer.ContainerFacade;
import com.mrv.lambdacontainer.exceptions.LambdaContainerException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Responsible for finding and injecting members with the @Inject annotation
 */
public class Injector {
    private final  ContainerFacade facade;

    public Injector(ContainerFacade facade) {
        this.facade = facade;
    }

    /**
     * Inject into fields and methods market with @Inject
     * @param object
     */
    public void injectInto(Object object) {
        this.injectInto(object, object.getClass());
    }

    /**
     * Inject into fields and methods market with @Inject.
     * @param object
     * @param clazz
     * @param <T>
     */
    public <T> void injectInto(Object object, Class<T> clazz) {
        processFields(object, clazz);
        processMethods(object, clazz);
    }

    protected<T> void processMethods(Object obj, Class<T> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if(isInjectableMethod(method)) {
                injectIntoMethod(method, obj, clazz);
            }
        }
    }

    private boolean isInjectableMethod(Method method) {
        int modifiers = method.getModifiers();

        return (method.getParameterCount() > 0)  &&
                Modifier.isPublic(modifiers) &&
                method.isAnnotationPresent(Inject.class);
    }

    private<T> void injectIntoMethod(Method method, Object obj, Class<T> clazz) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = getParameterInstances(parameterTypes);

        try {
            method.invoke(obj, parameters);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new LambdaContainerException(
                String.format("Error injectiong into %s.%s", clazz.getName(), method.getName()),
                ex
            );
        }
    }

    private Object[] getParameterInstances(Class<?>[] parameterTypes) {
        List<Object> parameters = new ArrayList<>();

        for (Class<?> parameterType : parameterTypes) {
            parameters.add(facade.resolve(parameterType));
        }

        return parameters.toArray();
    }

    protected<T> void processFields(Object obj, Class<T> clazz) {
        Field[] fields  = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                injectIntoField(field, obj, clazz);
            }
        }
    }

    private<T> void injectIntoField(Field field, Object obj, Class<T> clazz) {
        Class<?> fieldType = field.getType();
        Object fieldValue = facade.resolve(fieldType);

        boolean originalAccessibility = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(obj, fieldValue);
        } catch (IllegalAccessException ex) {
            throw new LambdaContainerException(
                String.format("Error injecting into %s.%s", clazz.getName(), field.getName()),
                ex
            );
        }
        field.setAccessible(originalAccessibility);
    }
}
