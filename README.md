# LambdaContainer
Yet another DIC(Powered with lambda expressions).

This is a personal project and my objective is to learn a bit more about Java + JUnit + Gradle. 

There are some nice solutions like Spring, Google Guice and Dagger but sometimes they seem overcomplicated when I need to do simple stuff. I also dislikes the excessive use of @Inject (from JSR-330) annotation at the target Objects. It's a personal opinion but I think the least one class know about how it is going to be used, the better. Also, I have a lot of FUN with DIY projects.

I decided to use Lambda Expressions motivated by [Fabien Potencier's work](http://fabien.potencier.org/) on [Twittee](http://twittee.org/) and its bigger brother [Pimple](http://pimple.sensiolabs.org/), both using PHP.

##Basic functionality

I ~~copied~~ got inspired by Google Guice fluid interface and decided to implement something like that. The two main classes a developer will interact are ContainerFacade and Scenario. The first contains an entry point to the services provided by the API and the second should be extended to configure the container.

```java

ContainerFacade cf = new ContainerFacade();
cf.setScenario(new MyScenarioImplementation());

//Lazy instantiation
FooInterface foo = cf.resolve(FooInterface.class);
```

###Scenario implementation example

```java
import com.mrv.lambdacontainer.Scenario;

public class MyScenarioImplementation  extends Scenario{
    @Override
    protected void setResolutions() {
        //clear all resolutions;
        clear();
        
        //Assign FooImplementation as a solution for FooInterface
        resolve(FooInterface.class).with(FooImplementation::new);
        // Obs: Different instances
        // assertNotSame(facade.resolve(FooInterface.class), facade.resolve(FooInterface.class));
        
        //Complex resolution
        resolve(BarInterface.class).with(
            () -> {
                BarImplementation bar = new BarImplementation();
                bar.setParameterA(parameterA);
                //(...)do whatever is needed to configure it(...)
                return bar;
            }
        );
        
        //Singleton resolution
        resolveSingle(QuxInterface.class).with(QuxImplementation::new);
        // Obs: Same instance
        // assertSame(facade.resolve(QuxInterface.class), facade.resolve(QuxInterface.class));
        
        
        //Extending existing solutions
        extend(FooInterface.class).with(
            (original) -> {
                original.setParameterA(parametarA);
                //(...)
                return original;
            }
        );
        
        //Override existing solutions
        override(BarInterface.class).with(BarImplementation::new);
    }
}

```

###Auto-resolution

If the container is asked to resolve a class/interface and can't find a resolver it will try one of the following:

* Find a default constructor and instantiate the object; or
* Find a constructor with parameter(s) it can resolve and instantiate the object; or
* Thrown LambdaContainerException.

So you can have something like this:

```java
public interface Simple {}

public class SimpleImplementation implements Simple{
    public Simple() {}
}

public class Composed {
    public Composed(SimpleImplementation simple) {}
}

public class ComposedWithInterface {
    public ComposedWithInterface(Simple simple) {}
}

//1 - will work. No need to define a scenario
facade.resolve(SimpleImplementation.class); 
//2 - will work. No need to define a scenario
facade.resolve(Composed.class);
//3 - will not work! Define a resolution for Simple interface first! 
facade.resolve(ComposedWithInterface.class); 
```

Number 3 can be arranged with :

```java
facade.setScenario(new Scenario() {
    @Override
    protected void setResolutions() {
        resolve(Simple.class)
            .with(SimpleImplementation::new);
    }
});

facade.resolve(ComposedWithInterface.class); //now it works!
```

###@Inject annotation support

There is limited support to @Inject annotation. It can be used at:

* Private/Protected/Public properties
* Public methods with one or more parameters.

The class:

```java
public class ToBeInjected {
    @Inject
    private Foo foo;
  
    @Inject
    public function injectHere(Bar bar, Quz quz) {}
}
```

Can be resolved and corrected "injected into" if the container can also resolve Foo, Bar and Quz:

```java
facade.setScenario(new Scenario() {
    @Override
    protected void setResolutions() {
        resolve(Foo.class)
            .with(FooImplementation::new);
            
        resolve(Bar.class)
            .with(BarImplementation::new);
        
        resolve(Quz.class)
            .with(QuzImplementation::new);
    }
});

facade.resolve(ToBeInjected.class);
```

**IMPORTANT**: If you are using lambda operators to resolve the class **ToBeInjected** you'll need to explicit inject the dependencies into it:

```java
public class FooScenario extends Scenario {
    @Override
    protected void setResolutions() {
        // resolution to FOO, Bar and Quz ommited for brevity
    
        resolve(ToBeInjected.class).with( () -> {
            ToBeInjected tbi = new ToBeInjected();
            injectInto(tbi); //foo, bar and quz are injected here
            tbi.doSomeStuff();
            return tbi;
        });
    }
}
```
