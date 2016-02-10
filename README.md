# LambdaContainer
Yet another DIC(Powered with lambda expressions).

This is a personal project and my objective is to learn a bit more about Java + JUnit + Gradle. 

There are some nice solutions like Spring, Google Guice and Dagger but sometimes they seem overcomplicated for my needs. I also dislikes the excessive use of @Inject (from JSR-330) annotation at the target Objects. It's a personal opinion but I think the least one class know about how it is going to be used, the better. Also, I have a lot of FUN with DIY projects.

I decided to use Lambda Expressions motivated by [Fabien Potencier's work](http://fabien.potencier.org/) on [Twittee](http://twittee.org/) and its bigger brother [Pimple](http://pimple.sensiolabs.org/), both using PHP.

##Basic functionality

###Simple scenario
Will retrieve a new instance each time an implementation is needed.

```java
Container container = new Container();
container.addResolution(
        SomeServiceInterface.class,
        () -> new SomeServiceImplementation();
);

SomeServiceInterface some = container.resolve(SomeServiceInterface.class);
some.doYourJob();

//different instances
assertNotSame(
        container.resolve(SomeServiceInterface.class), 
        container.resolve(SomeServiceInterface.class)
);
```

###Complex scenario
Can be used to configure complex objects.

```java
container.addResolution(
        SomeServiceInterface.class,
        () -> {
              SomeServiceImplementation some = new SomeServiceImplementation(c.resolve(OtherInterface.class));
              some->setParameterA(someParameter);
              some->setParameterB(otherParameter);
              some->callMethod();
              //(...)
              return some;
        }
);
```

###Autobuild 

If a class has no resolution defined the container will try to instantiate its dependencies and retrieve it.

```java
public class ClassWithNoResolution() {
        public ClassWithNoResolution (SomeInterface service1, OtherInterface service2) {
                //(...)
        }
}

container.addResolution(
        SomeInterface.class,
        () -> new SomeImplementation();
);

container.addResolution(
        OtherInterface.class,
        () -> new OtherImplementation();
);

//will work
ClassWithNoResolution obj = container.resolve(ClassWithNoResolution.class);

```

###Single instance support 
Will retrieve the same instance each time an implementation is needed.

```java
container.addSingleResolution(
        SomeServiceInterface.class,
        () -> new SomeServiceImplementation();
);

//Same instances
assertSame(
        container.resolve(SomeServiceInterface.class), 
        container.resolve(SomeServiceInterface.class)
);
```
###Resolution extension
Used to add new extra configuration via decoration.

```java
container.addResolution(
        SomeServiceInterface.class,
        () -> new SomeServiceImplementation();
);

container.extend(
        SomeServiceInterface.class,
        (original) -> {
                original.setParameterA(someParameter);
                return original;
        }
)
SomeServiceInterface someService = container.resolve(SomeServiceInterfave.class);

assertEquals(someParameter, someService.getParameterA());
```

## What to do next...

* Support to @Inject annotation
* Implement an interface a little bit more fluid
* Support for different configuration scenarios
* Simplify resolution insertion when there is no subclassing or interface implementation:

 ```java
//instead of 
container.addResolution(
        SomeServiceImplementation.class,
        () -> new SomeServiceImplementation();
);

//Should be nice to have:
container.addResolution(
        () -> new SomeServiceImplementation();
);
 ```
