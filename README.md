# LambdaContainer
Yet another DIC(with support of lambda expressions).

This is a personal project and my objective is to learn a bit more about Java + JUnit + Gradle. 

There are some nice solutions like Spring, Google Guice and Dagger but sometimes they seem overcomplicated for my needs. I also dislikes a little bit the use of @Inject (from JSR-330) annotation at the target Objects. It's a personal opinion but I think the least one class know about how it is going to be used, the better. Also, I have a lot of FUN with DIY projects.

I decided to use Lambda Expressions motivated by [Fabien Potencier's work](http://fabien.potencier.org/) on [Twittee](http://twittee.org/) and his bigger brother [Pimple](http://pimple.sensiolabs.org/), both using PHP.

My goal is to create something that works like that:

```java
Container container = new Container();

//Simple scenario
container.addMap(
        SomeServiceInterface.class,
        (c) -> new SomeServiceImplementation();
);

SomeServiceInterface some = container.resolve(SomeServiceInterface.class);
some.doYourJob();

//Complex scenario
container.addMap(
        SomeServiceInterface.class,
        (c) -> {
              SomeServiceImplementation some = new SomeServiceImplementation(c.resolve(OtherInterface.class));
              some->setParameterA(c.get("parameterA"));
              some->callMethod();
              //(...)
              return some;
        }
);
```

